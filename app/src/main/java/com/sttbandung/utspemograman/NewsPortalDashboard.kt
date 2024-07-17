package com.sttbandung.utspemograman

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sttbandung.uts_pemograman.R

class NewsPortalDashboard : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var adapterList: AdapterList
    private lateinit var itemList: MutableList<ItemList>
    private lateinit var db: FirebaseFirestore
    private lateinit var progressDialog: ProgressDialog
    private lateinit var mAth: FirebaseAuth

    private fun initializeViews() {
        recyclerView = findViewById(R.id.rcvNews)
        floatingActionButton = findViewById(R.id.floatAddNews)
        progressDialog = ProgressDialog(this).apply {
            setTitle("Loading...")
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemList = ArrayList()
        adapterList = AdapterList(itemList)
        recyclerView.adapter = adapterList
    }

    private fun getData() {
        progressDialog.show()
        db.collection("News_collection")
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    itemList.clear()
                    for (document in task.result) {
                        val item = ItemList(
                            document.id,
                            document.getString("title") ?: "",
                            document.getString("desc") ?: "",
                            document.getString("imageUrl") ?: ""
                        )
                        itemList.add(item)
                        Log.d("data", "${document.id} => ${document.data}")
                    }
                    adapterList.notifyDataSetChanged()
                } else {
                    Log.w("data", "error getting document", task.exception)
                }
                progressDialog.dismiss()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_portal_dashboard)
        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()
        mAth = FirebaseAuth.getInstance()


        val recyclerView = findViewById<RecyclerView>(R.id.rcvNews)
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatAddNews)
        progressDialog = ProgressDialog(this@NewsPortalDashboard).apply{
            setTitle("Loading...")
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemList = ArrayList()
        adapterList = AdapterList(itemList)
        recyclerView.adapter = adapterList

        adapterList.setOnItemClickListener(object: AdapterList.OnItemClickListener{
            override fun onItemClick(item: ItemList) {
                val intent = Intent(this@NewsPortalDashboard, DetailMinum::class.java).apply {
                    putExtra("id", item.id)
                    putExtra("title", item.judul)
                    putExtra("desc", item.description)
                    putExtra("imageUrl", item.imageUrl)
                }
                startActivity(intent)
            }
        })


        floatingActionButton.setOnClickListener {
            val toAddPage = Intent(this@NewsPortalDashboard, NewsAdd::class.java)
            startActivity(toAddPage)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_logout) {
            mAth.signOut()
            Toast.makeText(this@NewsPortalDashboard, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@NewsPortalDashboard, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onStart() {
        super.onStart()
        getData()
    }
}

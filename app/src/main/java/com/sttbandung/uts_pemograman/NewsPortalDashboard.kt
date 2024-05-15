package com.sttbandung.uts_pemograman

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewsPortalDashboard : AppCompatActivity() {
    private lateinit var listtext: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_news_portal_dashboard)

        listtext = findViewById(R.id.produklist)

        val product = arrayOf(
         "Coffee ",
          "Tea", "Hot chocolate", "Green tea ", "milk","Iced tea","Iced coffee"

        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, product)
        listtext.adapter = adapter

    }
}
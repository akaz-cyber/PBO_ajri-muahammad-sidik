package com.sttbandung.utspemograman

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sttbandung.uts_pemograman.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var Textemail: EditText
    private lateinit var Textusername: EditText
    private lateinit var Textpassword: EditText
    private lateinit var Textconfirm: EditText
    private lateinit var btnregister: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        Textemail = findViewById(R.id.emailinput)
        Textusername = findViewById(R.id.usernameinput)
        Textpassword = findViewById(R.id.passwordinput)
        Textconfirm = findViewById(R.id.confirmpassword)
        btnregister = findViewById(R.id.buttonregistration)
        progressBar = findViewById(R.id.progressBar)

        btnregister.setOnClickListener {
            val email = Textemail.text.toString()
            val username = Textusername.text.toString()
            val password = Textpassword.text.toString()
            val confirmPassword = Textconfirm.text.toString()



            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (!email.contains('@') || !email.contains(".com")) {
                    Textemail.error = "Email Not valid"
                } else if (password.length < 6) {
                    Textpassword.error = "Password must not be less than 6 characters"
                } else if (password == confirmPassword) {
                    progressBar.visibility = View.GONE
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            Log.d("RegisterActivity", "User created successfully")
                            val userId = mAuth.currentUser?.uid
                            val user = hashMapOf(
                                "username" to username,
                                "email" to email
                            )
                            userId?.let {
                                db.collection("users").document(it)
                                    .set(user)
                                    .addOnSuccessListener {
                                        Log.d("RegisterActivity", "User data added to Firestore")
                                        Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, LoginActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("RegisterActivity", "Failed to add user data to Firestore", e)
                                        Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Log.e("RegisterActivity", "Registration failed", task.exception)
                            Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Textconfirm.error = "Passwords do not match"
                }
            } else {
                Toast.makeText(this, "Please input all fields", Toast.LENGTH_SHORT).show()
            }
        }

        var Onclicklogin = findViewById<TextView>(R.id.pindah_registration)
        Onclicklogin.setOnClickListener {
            var intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

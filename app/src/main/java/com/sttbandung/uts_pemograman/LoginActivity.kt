package com.sttbandung.uts_pemograman

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    private lateinit var Textemailinput: EditText
    private lateinit var Textpasswordinput: EditText
    private lateinit var btnlogin: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

     Textemailinput = findViewById(R.id.emailinput)
     Textpasswordinput = findViewById(R.id.passwordinput)
     btnlogin = findViewById(R.id.buttonlogin)

     btnlogin.setOnClickListener{
         val email = Textemailinput.text.toString()
         val password = Textpasswordinput.text.toString()

         if(email.isNotEmpty() && password.isNotEmpty()){

            if (email == RegisterActivity.registemailconfirm && password == RegisterActivity.registpasswordconfirm){
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NewsPortalDashboard::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Email and password wrong", Toast.LENGTH_SHORT).show()
            }

         } else {
             Toast.makeText(this, "Please input all field", Toast.LENGTH_SHORT).show()
         }


     }
      val Onclikregister = findViewById<TextView>(R.id.login_pindah)
        Onclikregister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }


}
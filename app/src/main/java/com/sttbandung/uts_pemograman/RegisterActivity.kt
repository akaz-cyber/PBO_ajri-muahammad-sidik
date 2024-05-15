package com.sttbandung.uts_pemograman

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var Textemail: EditText
    private lateinit var Textusername: EditText
    private lateinit var Textpassword: EditText
    private lateinit var Textconfirm: EditText
    private lateinit var btnregister: Button

    companion object{
        var registemailconfirm: String = ""
        var registpasswordconfirm: String = ""

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

         Textemail = findViewById(R.id.emailinput)
        Textusername = findViewById(R.id.usernameinput)
        Textpassword = findViewById(R.id.passwordinput)
        Textconfirm = findViewById(R.id.confirmpassword)
        btnregister = findViewById(R.id.buttonregistration)


        btnregister.setOnClickListener {
            val email = Textemail.text.toString()
            val username = Textusername.text.toString()
            val password = Textpassword.text.toString()
            val confirmPassword = Textconfirm.text.toString()

            if(email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){

                if (!email.contains('@') ||!email.contains(".com")){
                    Textemail.error =  "Email Not valid"

                }else if (password.length < 6 ) {
                    Textpassword.error = "password must not be less than 6 lines"
                } else if (password == confirmPassword){


                    registemailconfirm = email
                    registpasswordconfirm = password


                    Toast.makeText(this, "Registeration success, please input yout account", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                    finish()

                }
                else {
                    Toast.makeText(this, "password do not match", Toast.LENGTH_SHORT).show()
                }

            }else {
                Toast.makeText(this, "Please input all field", Toast.LENGTH_SHORT).show()
            }


        }


        var Onclicklogin = findViewById<TextView>(R.id.pindah_registration)
        Onclicklogin.setOnClickListener {
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }








}
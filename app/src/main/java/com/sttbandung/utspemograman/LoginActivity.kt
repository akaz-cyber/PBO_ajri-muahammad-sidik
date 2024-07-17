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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.sttbandung.uts_pemograman.R

class LoginActivity : AppCompatActivity() {

     companion object{
     private const val RC_SIGN_IN = 9001
         private const val TAG = "LoginActivity"

   }

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var Textemailinput: EditText
    private lateinit var Textpasswordinput: EditText
    private lateinit var btnlogin: Button
    private lateinit var googleSignInButton: Button

    private fun firebaseAuthWithGoogle(acct:GoogleSignInAccount){
        Log.d(TAG,"firebaseAuthWithGoogle" + acct.id)
        val credential: AuthCredential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    Log.d(TAG,"signWithCredetial:success")
                    val user:FirebaseUser? = mAuth.currentUser
                    Toast.makeText(this, "Authentification successfull", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, NewsPortalDashboard::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Log.d(TAG,"signWithCredetial:failure",task.exception)
                    Toast.makeText(this, "authentification failed", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.GONE
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            }catch (e:ApiException){
                Log.w(TAG,"Google sign in failed", e)
                progressBar.visibility = View.GONE
                Toast.makeText(this, "google sign failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn() {
        progressBar.visibility = View.VISIBLE
        val signInAccount = mGoogleSignInClient.signInIntent
        startActivityForResult(signInAccount, RC_SIGN_IN)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

     Textemailinput = findViewById(R.id.emailinput)
     Textpasswordinput = findViewById(R.id.passwordinput)
     btnlogin = findViewById(R.id.buttonlogin)
        googleSignInButton = findViewById(R.id.googleSignInButton)
        progressBar = findViewById(R.id.progressBar)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)
        mAuth = FirebaseAuth.getInstance()



     btnlogin.setOnClickListener{
         val email = Textemailinput.text.toString()
         val password = Textpasswordinput.text.toString()

         if (email.isNotEmpty() && password.isNotEmpty()) {
             progressBar.visibility = View.VISIBLE
             mAuth.signInWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this) { task ->
                     progressBar.visibility = View.GONE
                     if (task.isSuccessful) {
                         Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                         val intent = Intent(this, NewsPortalDashboard::class.java)
                         startActivity(intent)
                         finish()
                     } else {
                         Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                     }
                 }
         } else {
             Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
         }


     }

        googleSignInButton.setOnClickListener {
            signIn()
        }


      val Onclikregister = findViewById<TextView>(R.id.login_pindah)
        Onclikregister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }


}
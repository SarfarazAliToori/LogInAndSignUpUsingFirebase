package com.wordpress.safbk.loginandsignupusingfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogIn : AppCompatActivity() {

    val TAG = "LogIn"
    private lateinit var tvIHaveNoAccount: TextView
    private lateinit var btnLogIn: Button
    private lateinit var userName: EditText
    private lateinit var userPass: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        myInit()
    }

    private fun myInit() {
        // Initialize Firebase Auth
        auth = Firebase.auth

        tvIHaveNoAccount = findViewById(R.id.tv_I_have_no_account)
        btnLogIn = findViewById(R.id.btn_login)
        userName = findViewById(R.id.ed_email_login)
        userPass = findViewById(R.id.ed_password_logIn)

        tvIHaveNoAccount.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        btnLogIn.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email = userName.text.toString().trim()
        val password = userPass.text.toString().trim()

        if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Please fill All Properly", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser.toString()
                    Toast.makeText(baseContext, "user: ${user}",
                        Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }

    }
}
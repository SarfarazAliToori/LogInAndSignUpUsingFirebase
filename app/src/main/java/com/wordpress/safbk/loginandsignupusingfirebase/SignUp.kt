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

class SignUp : AppCompatActivity() {

    val TAG = "SignUP"
    private lateinit var tvIHaveAccrount: TextView
    private lateinit var btnSignUp: Button
    private lateinit var userName: EditText
    private lateinit var userPass: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        myInit()
    }

    private fun myInit() {
        // Initialize Firebase Auth
        auth = Firebase.auth

        tvIHaveAccrount = findViewById(R.id.tv_I_have_account)
        btnSignUp = findViewById(R.id.btn_SignUp)
        userName = findViewById(R.id.ed_email_signUp)
        userPass = findViewById(R.id.ed_password_signUp)


        tvIHaveAccrount.setOnClickListener{
            startActivity(Intent(this, LogIn::class.java))
        }

        btnSignUp.setOnClickListener {
            performSignUp()
        }
    }

    private fun performSignUp() {

        val email = userName.text.toString().trim()
        val password = userPass.text.toString().trim()
        if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Please Fill All...", Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }


//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            // reload your activity.
//            //reload();
//        }
//    }
}
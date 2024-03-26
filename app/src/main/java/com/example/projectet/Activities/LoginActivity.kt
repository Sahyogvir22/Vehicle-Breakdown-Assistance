package com.example.projectet.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectet.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    var mSignInClient: GoogleSignInClient? = null
    var firebaseAuth: FirebaseAuth? = null
    var progressBar: ProgressDialog? = null
    var signInButton: SignInButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        progressBar = ProgressDialog(this)
        progressBar!!.setTitle("Please Wait...")
        progressBar!!.setMessage("We are setting Everything for you...")
        progressBar!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        signInButton = findViewById<View>(R.id.GoogleSignBtn) as SignInButton

        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mSignInClient = GoogleSignIn.getClient(applicationContext, signInOptions)

        signInButton!!.setOnClickListener {
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val googleSignInAccountTask = GoogleSignIn
                .getSignedInAccountFromIntent(data)
            if (googleSignInAccountTask.isSuccessful) {
                progressBar!!.show()
                try {
                    val googleSignInAccount = googleSignInAccountTask.getResult(
                        ApiException::class.java
                    )
                    if (googleSignInAccount != null) {
                        val authCredential = GoogleAuthProvider
                            .getCredential(googleSignInAccount.idToken, null)
                        firebaseAuth!!.signInWithCredential(authCredential).addOnCompleteListener(
                            this
                        ) { task ->
                            if (task.isSuccessful) {
                                val database = FirebaseDatabase.getInstance()
                                val myRef = database.reference.child("users")

                                val user_details = HashMap<String, Any>()

                                val id = googleSignInAccount.id.toString()
                                val name = googleSignInAccount.displayName.toString()
                                val mail = googleSignInAccount.email.toString()
                                val pic = googleSignInAccount.photoUrl.toString()

                                user_details["id"] = id
                                user_details["name"] = name
                                user_details["mail"] = mail
                                user_details["profilepic"] = pic
                                user_details["role"] = "empty"

                                myRef.child(id).updateChildren(user_details)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            progressBar!!.cancel()
                                            Log.d("TAG", "onComplete: Signedin")
                                            val intent = Intent(
                                                applicationContext,
                                                RoleActivity::class.java
                                            )
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                            startActivity(intent)
                                        }
                                    }
                            }
                        }
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Couldn't sign in.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
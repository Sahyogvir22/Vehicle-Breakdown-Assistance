package com.example.projectet.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.projectet.MainActivity
import com.example.projectet.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.FirebaseDatabase

class RoleActivity : AppCompatActivity() {
    var userBtn: Button? = null
    var adminBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)
        val userId = GoogleSignIn.getLastSignedInAccount(applicationContext)!!
            .id
        userBtn = findViewById<View>(R.id.UserBtn) as Button
        adminBtn = findViewById<View>(R.id.AdminBtn) as Button

        userBtn!!.setOnClickListener {
            FirebaseDatabase.getInstance().reference.child("users")
                .child(userId!!)
                .child("role")
                .setValue("user").addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Navigating to MainActivity
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
        }

        adminBtn!!.setOnClickListener {
            FirebaseDatabase.getInstance().reference.child("users")
                .child(userId!!)
                .child("role")
                .setValue("admin").addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(applicationContext, AdminActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
        }
    }
}
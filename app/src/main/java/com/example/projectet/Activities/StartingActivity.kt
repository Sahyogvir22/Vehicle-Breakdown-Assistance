package com.example.projectet.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.projectet.R

class StartingActivity : AppCompatActivity() {
    var getStartedBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting)
        getStartedBtn = findViewById<View>(R.id.GetStartedBtn) as Button

        getStartedBtn!!.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
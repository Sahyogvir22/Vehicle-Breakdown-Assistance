package com.example.projectet.Activities

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projectet.Fragments.AddMechanicFragment
import com.example.projectet.Fragments.ShowMechanicFragment
import com.example.projectet.Fragments.UserProfileFragment
import com.example.projectet.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminActivity : AppCompatActivity() {
    var frameLayout: FrameLayout? = null
    var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)



        frameLayout = findViewById<View>(R.id.FragmentContainerAdmin) as FrameLayout

        bottomNavigationView = findViewById<View>(R.id.BottomNavigationViewAdmin) as BottomNavigationView
        val menuNav = bottomNavigationView!!.menu

        bottomNavigationView!!.setOnNavigationItemSelectedListener(bottomNavigationMethod)
    }

    private val bottomNavigationMethod =
        BottomNavigationView.OnNavigationItemSelectedListener { item -> //Assigining Fragment as Null
            var fragment: Fragment? = null
                        supportFragmentManager.beginTransaction().replace(
                R.id.FragmentContainerAdmin,
                fragment!!
            ).commit()
            true
        }
}
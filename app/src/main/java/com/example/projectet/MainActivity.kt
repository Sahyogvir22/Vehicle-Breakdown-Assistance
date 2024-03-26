package com.example.projectet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projectet.Activities.AdminActivity
import com.example.projectet.Activities.RoleActivity
import com.example.projectet.Activities.StartingActivity
import com.example.projectet.Fragments.HomeFragment
import com.example.projectet.Fragments.UserProfileFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    var frameLayout: FrameLayout? = null
    var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameLayout = findViewById<View>(R.id.FragmentContainer) as FrameLayout
        bottomNavigationView = findViewById<View>(R.id.BottomNavigationView) as BottomNavigationView
        supportFragmentManager.beginTransaction().replace(R.id.FragmentContainer, HomeFragment())
            .commit()
        bottomNavigationView!!.setOnNavigationItemSelectedListener(bottomNavigationMethod)
    }

    override fun onStart() {
        super.onStart()
        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser == null) {
            val intent = Intent(this@MainActivity, StartingActivity::class.java)
            startActivity(intent)
        } else {
            val id = GoogleSignIn.getLastSignedInAccount(applicationContext)!!.id
            val reference = FirebaseDatabase.getInstance().reference.child("users").child(
                id!!
            ).child("role")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.value.toString()
                    Toast.makeText(applicationContext, data, Toast.LENGTH_SHORT).show()

                    if (data == "admin") {
                        val intent = Intent(applicationContext, AdminActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else if (data != "user") {
                        val intent = Intent(applicationContext, RoleActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

    private val bottomNavigationMethod =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.homeMenu -> fragment = HomeFragment()
                R.id.profileMenu -> fragment = UserProfileFragment()
            }
            supportFragmentManager.beginTransaction().replace(
                R.id.FragmentContainer,
                fragment!!
            ).commit()
            true
        }
}
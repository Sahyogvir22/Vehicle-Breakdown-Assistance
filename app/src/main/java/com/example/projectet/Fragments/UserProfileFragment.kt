package com.example.projectet.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.projectet.Activities.StartingActivity
import com.example.projectet.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserProfileFragment : Fragment() {
    var userName: TextView? = null
    var circleImageView: CircleImageView? = null
    var signOutBtn: Button? = null
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.activity_user_profile_fragment, container, false)
        userName = view.findViewById<View>(R.id.UserNametxt) as TextView
        circleImageView = view.findViewById<View>(R.id.UserProfileImg) as CircleImageView
        signOutBtn = view.findViewById<View>(R.id.Signoutbtn) as Button

        val acct = GoogleSignIn.getLastSignedInAccount(activity)
        if (acct != null) {
            userName!!.text = acct.displayName
            Picasso.get().load(acct.photoUrl).into(circleImageView)
        }


        signOutBtn!!.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

            val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            googleSignInClient.signOut().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(context, StartingActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
            }
        }
        return view
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): UserProfileFragment {
            val fragment = UserProfileFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
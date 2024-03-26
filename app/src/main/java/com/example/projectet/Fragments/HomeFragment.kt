package com.example.projectet.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectet.Adapters.HomeAdapter
import com.example.projectet.Model.Model
import com.example.projectet.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class HomeFragment : Fragment() {
    var recyclerView: RecyclerView? = null
    var adapter: HomeAdapter? = null
    var searchBtn: Button? = null
    var vehicleNameEditTxt: EditText? = null
    var locationEditTxt: EditText? = null

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
        val view: View = inflater.inflate(R.layout.activity_home_fragment, container, false)

        recyclerView = view.findViewById<View>(R.id.HomeRecyclerVeiw) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        locationEditTxt = view.findViewById<View>(R.id.LocationEditTxtHome) as EditText
        vehicleNameEditTxt = view.findViewById<View>(R.id.VehicleEditTxtHome) as EditText
        searchBtn = view.findViewById<View>(R.id.SearchBtn) as Button
        searchBtn!!.setOnClickListener {
            val location =
                locationEditTxt!!.text.toString().lowercase(Locale.getDefault())
            val vehicle =
                vehicleNameEditTxt!!.text.toString().lowercase(Locale.getDefault())
            if (location.isEmpty() || vehicle.isEmpty()) {
                Toast.makeText(context, "Please,Fill details", Toast.LENGTH_SHORT).show()
            } else {

                val options: FirebaseRecyclerOptions<Model> =
                    FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(
                            FirebaseDatabase.getInstance().reference.child("mechanics")
                                .child(vehicle + location),
                            Model::class.java
                        )
                        .build()
                adapter = HomeAdapter(options)
                recyclerView!!.adapter = adapter
                adapter!!.startListening()
            }
        }

        val options: FirebaseRecyclerOptions<Model> = FirebaseRecyclerOptions.Builder<Model>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("allmechanics"),
                Model::class.java
            )
            .build()
        adapter = HomeAdapter(options)
        recyclerView!!.adapter = adapter
        return view
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
package com.example.projectet.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectet.Adapters.MechanicAdapter
import com.example.projectet.Model.Model
import com.example.projectet.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class ShowMechanicFragment : Fragment() {
    var recyclerView: RecyclerView? = null
    var adapter: MechanicAdapter? = null

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
        val view: View = inflater.inflate(R.layout.activity_show_mechanic_fragment, container, false)

        recyclerView = view.findViewById<View>(R.id.ShowAllMechanicRecyclerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        val options: FirebaseRecyclerOptions<Model> = FirebaseRecyclerOptions.Builder<Model>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("allmechanics"),
                Model::class.java
            )
            .build()
        adapter = MechanicAdapter(options)
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
        fun newInstance(param1: String?, param2: String?): ShowMechanicFragment {
            val fragment = ShowMechanicFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
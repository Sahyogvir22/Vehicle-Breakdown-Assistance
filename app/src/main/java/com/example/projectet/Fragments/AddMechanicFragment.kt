package com.example.projectet.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projectet.R
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class AddMechanicFragment : Fragment() {
    var mechanicNameEditTxt: EditText? = null
    var mechanicNumberEditTxt: EditText? = null
    var mechanicLocationEditTxt: EditText? = null
    var mechanicPriceEditTxt: EditText? = null
    var vehicleNameEditTxt: EditText? = null
    var addMechanicDetailsBtn: Button? = null

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
        val view: View = inflater.inflate(R.layout.activity_add_mechanic_fragment, container, false)
        mechanicNameEditTxt = view.findViewById<View>(R.id.MechanicNameEditTxt) as EditText
        mechanicNumberEditTxt = view.findViewById<View>(R.id.MechanicNumberEditTxt) as EditText
        mechanicLocationEditTxt = view.findViewById<View>(R.id.LocationEditTxt) as EditText
        mechanicPriceEditTxt = view.findViewById<View>(R.id.PriceEditTxt) as EditText
        vehicleNameEditTxt = view.findViewById<View>(R.id.VehicleNameEditTxt) as EditText
        addMechanicDetailsBtn = view.findViewById<View>(R.id.AddNotificationBtn) as Button

        addMechanicDetailsBtn!!.setOnClickListener { view ->
            val MechanicName = mechanicNameEditTxt!!.text.toString().trim { it <= ' ' }
            val MechanicNumber = mechanicNumberEditTxt!!.text.toString().trim { it <= ' ' }
            val MechanicLocaiton = mechanicLocationEditTxt!!.text.toString().trim { it <= ' ' }
            val MechanicPrice = mechanicPriceEditTxt!!.text.toString().trim { it <= ' ' }
            val vehicleName = vehicleNameEditTxt!!.text.toString().trim { it <= ' ' }

            if (MechanicName.isEmpty() || MechanicNumber.isEmpty() || MechanicLocaiton.isEmpty() || MechanicPrice.isEmpty() || vehicleName.isEmpty()) {
                Toast.makeText(view.context, "Please,Enter all the details", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Add_Mechanic_Method(
                    MechanicName,
                    MechanicNumber,
                    MechanicLocaiton,
                    MechanicPrice,
                    vehicleName
                )
            }
        }
        return view
    }

    private fun Add_Mechanic_Method(
        mechanicName: String,
        mechanicNumber: String,
        mechanicLocaiton: String,
        mechanicPrice: String,
        vehicleName: String
    ) {
        val key = FirebaseDatabase.getInstance().reference.child("mechanics").push().key

        val user_details = HashMap<String, Any>()

    user_details["mechanicLocation"] = mechanicLocaiton
        user_details["mechanicNumber"] = mechanicNumber
        user_details["mechanicPrice"] = mechanicPrice
        user_details["vehicleName"] = vehicleName
        user_details["mechanicName"] = mechanicName
        val searchTxt = vehicleName + mechanicLocaiton.lowercase(Locale.getDefault())

        FirebaseDatabase.getInstance().reference.child("mechanics").child(searchTxt).child(key!!)
            .updateChildren(user_details).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FirebaseDatabase.getInstance().reference.child("allmechanics").child(key)
                        .updateChildren(user_details).addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                mechanicNameEditTxt!!.setText("")
                                mechanicNumberEditTxt!!.setText("")
                                mechanicLocationEditTxt!!.setText("")
                                mechanicPriceEditTxt!!.setText("")
                                vehicleNameEditTxt!!.setText("")
                            }
                        }
                } else {
                    Toast.makeText(context, "Please,Add details again", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): AddMechanicFragment {
            val fragment = AddMechanicFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
package com.example.projectet.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectet.Model.Model
import com.example.projectet.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class HomeAdapter(options: FirebaseRecyclerOptions<Model>) :
    FirebaseRecyclerAdapter<Model, HomeAdapter.Viewholder>(options) {
    protected override fun onBindViewHolder(holder: Viewholder, position: Int, model: Model) {

        val context = holder.itemView.context

        holder.nameTxt.setText(model.mechanicName)
        holder.phoneNumberTxt.setText(model.mechanicNumber)
        holder.LocationTxt.setText(model.mechanicLocation)
        holder.priceTxt.setText(model.mechanicPrice)
        holder.vechicleNameTxt.setText(model.vehicleName)

        holder.mCallMechanicBtn.setOnClickListener { //Accessing users Phone Number
            val phone: String = model.mechanicNumber!!

            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.user_data_layout, parent, false)
        return Viewholder(view)
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTxt: TextView
        var phoneNumberTxt: TextView
        var vechicleNameTxt: TextView
        var LocationTxt: TextView
        var priceTxt: TextView
        var mCallMechanicBtn: Button

        init {

            nameTxt = itemView.findViewById<View>(R.id.NameTxt) as TextView
            phoneNumberTxt = itemView.findViewById<View>(R.id.PhoneNumberTxt) as TextView
            vechicleNameTxt = itemView.findViewById<View>(R.id.VehicleNameTxt) as TextView
            LocationTxt = itemView.findViewById<View>(R.id.LocationTxt) as TextView
            priceTxt = itemView.findViewById<View>(R.id.PriceTxt) as TextView
            mCallMechanicBtn = itemView.findViewById<View>(R.id.CallMechanicBtn) as Button
        }
    }
}


package com.example.projectet.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectet.Model.Model
import com.example.projectet.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MechanicAdapter(options: FirebaseRecyclerOptions<Model>) :
    FirebaseRecyclerAdapter<Model, MechanicAdapter.Viewholder>(options) {
    protected override fun onBindViewHolder(holder: Viewholder, position: Int, model: Model) {


        holder.nameTxt.setText(model.mechanicName)
        holder.phoneNumberTxt.setText(model.mechanicNumber)
        holder.LocationTxt.setText(model.mechanicLocation)
        holder.priceTxt.setText(model.mechanicPrice)
        holder.vechicleNameTxt.setText(model.vehicleName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.single_data_file, parent, false)
        return Viewholder(view)
    }


    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTxt: TextView
        var phoneNumberTxt: TextView
        var vechicleNameTxt: TextView
        var LocationTxt: TextView
        var priceTxt: TextView

        init {

            nameTxt = itemView.findViewById<View>(R.id.NameTxt) as TextView
            phoneNumberTxt = itemView.findViewById<View>(R.id.PhoneNumberTxt) as TextView
            vechicleNameTxt = itemView.findViewById<View>(R.id.VehicleNameTxt) as TextView
            LocationTxt = itemView.findViewById<View>(R.id.LocationTxt) as TextView
            priceTxt = itemView.findViewById<View>(R.id.PriceTxt) as TextView
        }
    }
}



package com.aurosaswat.universalotptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OTPAdapter(private val mlists:List<ItemsViewmodel>): RecyclerView.Adapter<OTPAdapter.ViewHolder>() {


    class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView){
       val sender_text=itemView.findViewById<TextView>(R.id.sender)
        val otp=itemView.findViewById<TextView>(R.id.otp)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.contact_otp,parent)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
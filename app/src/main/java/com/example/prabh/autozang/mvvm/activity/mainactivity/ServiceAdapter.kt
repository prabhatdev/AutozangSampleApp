package com.example.prabh.autozang.mvvm.activity.mainactivity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prabh.autozang.R
import com.example.prabh.autozang.room.table.ServiceCenters
import kotlinx.android.synthetic.main.service_centers_recycler.view.*


class ServiceAdapter(val serviceCentres: ArrayList<ServiceCenters>) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.service_centers_recycler, parent, false))

    override fun getItemCount(): Int = serviceCentres.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(serviceCentres[position],position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(serviceCenters: ServiceCenters, position: Int) {
            itemView.name.text=serviceCenters.name
            itemView.location_text.text=serviceCenters.location
            itemView.distance.text=serviceCenters.distance.toString()+" Km Away"
            itemView.reviews_text.text=serviceCenters.reviews.toString()+" reviews"
            itemView.working_days.text=serviceCenters.timings
            itemView.price.text="Rs "+serviceCenters.price.toString()
            if(position%2==0) {
                itemView.image.setImageResource(R.drawable.capture1)
            }
            else {
                itemView.image.setImageResource(R.drawable.capture2)
            }
        }
    }
}
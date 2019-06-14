package com.maas.soft.i_eye.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maas.soft.i_eye.R

class PlaceAdapter(var placeItems: ArrayList<String>) : RecyclerView.Adapter<PlaceViewHolder>() {
    private lateinit var onItemClick:View.OnClickListener

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        mainView.setOnClickListener(onItemClick)
        return PlaceViewHolder(mainView)
    }

    override fun getItemCount(): Int = placeItems.size

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.placeName.text = placeItems[position]
    }

}
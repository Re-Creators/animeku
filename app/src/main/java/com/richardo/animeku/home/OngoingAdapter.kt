package com.richardo.animeku.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeku.OngoingListQuery
import com.richardo.animeku.R
import com.richardo.animeku.viewholder.OngoingViewHolder
import kotlinx.android.synthetic.main.ongoing_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class OngoingAdapter(private val from : String) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var data = listOf<OngoingListQuery.Medium>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OngoingViewHolder.create(parent, from)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = data[position]
        if (currentData != null){
            (holder as OngoingViewHolder).bind(currentData)
        }
    }


}
package com.richardo.animeku.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeku.UpcomingListQuery
import com.richardo.animeku.R
import kotlinx.android.synthetic.main.upcoming_item.view.*

class UpcomingAdapter : RecyclerView.Adapter<UpcomingAdapter.MyViewHolder>(){

    var data = listOf<UpcomingListQuery.Medium>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onItemClick : ((Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upcoming_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val upcoming = data[position]
        holder.bind(upcoming)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(upcoming.id)
        }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(upcoming: UpcomingListQuery.Medium){
            itemView.img_upcoming.apply {
                Glide.with(context)
                    .load(upcoming.coverImage?.extraLarge)
                    .into(this)
            }
            itemView.tv_upcoming_title.text = upcoming.title?.romaji ?: ""
        }
    }

}
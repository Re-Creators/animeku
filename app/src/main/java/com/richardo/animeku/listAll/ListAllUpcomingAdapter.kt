package com.richardo.animeku.listAll

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.UpcomingListQuery
import com.richardo.animeku.viewholder.TagViewHolder

class ListAllUpcomingAdapter :
    PagingDataAdapter<UpcomingListQuery.Medium, RecyclerView.ViewHolder>(ListAllUpcomingDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TagViewHolder.create(parent, "List All")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)
        if (currentData != null){
            (holder as TagViewHolder).bindItemUpcoming(currentData)
        }
    }

}

class ListAllUpcomingDiffCallback : DiffUtil.ItemCallback<UpcomingListQuery.Medium>(){
    override fun areItemsTheSame(
        oldItem: UpcomingListQuery.Medium,
        newItem: UpcomingListQuery.Medium
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UpcomingListQuery.Medium,
        newItem: UpcomingListQuery.Medium
    ): Boolean {
        return newItem == oldItem
    }

}
package com.richardo.animeku.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.OngoingListQuery
import com.richardo.animeku.viewholder.OngoingViewHolder


class OngoingAdapter(private val from : String)
    : ListAdapter<OngoingListQuery.Medium, RecyclerView.ViewHolder>(OngoingDiff()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OngoingViewHolder.create(parent, from)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)
        (holder as OngoingViewHolder).bind(currentData)
    }

}

private class OngoingDiff : DiffUtil.ItemCallback<OngoingListQuery.Medium>(){
    override fun areItemsTheSame(
        oldItem: OngoingListQuery.Medium,
        newItem: OngoingListQuery.Medium
    ): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(
        oldItem: OngoingListQuery.Medium,
        newItem: OngoingListQuery.Medium
    ): Boolean {
        return oldItem == newItem
    }

}
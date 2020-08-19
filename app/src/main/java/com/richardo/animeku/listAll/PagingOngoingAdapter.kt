package com.richardo.animeku.listAll

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.OngoingListQuery
import com.richardo.animeku.viewholder.OngoingViewHolder

class PagingOngoingAdapter : PagingDataAdapter<OngoingListQuery.Medium, RecyclerView.ViewHolder>(PagingOngoingDiffCall()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OngoingViewHolder.create(parent, "List All")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)

        if (currentData != null){
            (holder as OngoingViewHolder).bind(currentData)

        }
    }


}

class PagingOngoingDiffCall : DiffUtil.ItemCallback<OngoingListQuery.Medium>(){
    override fun areItemsTheSame(
        oldItem: OngoingListQuery.Medium,
        newItem: OngoingListQuery.Medium
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: OngoingListQuery.Medium,
        newItem: OngoingListQuery.Medium
    ): Boolean {
        return newItem == oldItem
    }

}
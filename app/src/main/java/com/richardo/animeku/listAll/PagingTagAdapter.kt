package com.richardo.animeku.listAll

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.SortListQuery
import com.richardo.animeku.viewholder.TagViewHolder


class PagingTagAdapter(private val from : String) : PagingDataAdapter<SortListQuery.Medium, RecyclerView.ViewHolder>(ListAllDiffCallback()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return TagViewHolder.create(parent, from)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)
        if (currentData != null){
            (holder as TagViewHolder).bindItemTag(currentData)
        }
    }

}

class ListAllDiffCallback : DiffUtil.ItemCallback<SortListQuery.Medium>(){
    override fun areItemsTheSame(
        oldItem: SortListQuery.Medium,
        newItem: SortListQuery.Medium
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SortListQuery.Medium,
        newItem: SortListQuery.Medium
    ): Boolean {
        return oldItem == newItem
    }

}
package com.richardo.animeku.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.SortListQuery
import com.richardo.animeku.viewholder.TagViewHolder

class TagAdapter(private val from : String)
    : ListAdapter<SortListQuery.Medium, RecyclerView.ViewHolder>(TagDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return TagViewHolder.create(parent, from)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)

        (holder as TagViewHolder).bindItemTag(currentData)
    }
}

class TagDiffCallback : DiffUtil.ItemCallback<SortListQuery.Medium>(){
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
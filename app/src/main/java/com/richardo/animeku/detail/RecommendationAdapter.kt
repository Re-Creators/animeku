package com.richardo.animeku.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.DetailAnimeQuery
import com.richardo.animeku.viewholder.TagViewHolder

class RecommendationAdapter
    : ListAdapter<DetailAnimeQuery.Node2, RecyclerView.ViewHolder>(RecommendationDiff()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TagViewHolder.create(parent, "Detail")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData =  getItem(position)

        if (currentData != null) {
            (holder as TagViewHolder).bindItemRecommendation(currentData)
        }
    }

}


private class RecommendationDiff : DiffUtil.ItemCallback<DetailAnimeQuery.Node2>(){
    override fun areItemsTheSame(
        oldItem: DetailAnimeQuery.Node2,
        newItem: DetailAnimeQuery.Node2
    ): Boolean {
        return oldItem.mediaRecommendation?.id == newItem.mediaRecommendation?.id
    }

    override fun areContentsTheSame(
        oldItem: DetailAnimeQuery.Node2,
        newItem: DetailAnimeQuery.Node2
    ): Boolean {
        return newItem == oldItem
    }

}
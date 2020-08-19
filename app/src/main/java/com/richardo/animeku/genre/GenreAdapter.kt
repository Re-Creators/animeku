package com.richardo.animeku.genre

import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.AnimeByGenreQuery
import com.richardo.animeku.viewholder.TagViewHolder

class GenreAdapter : PagingDataAdapter<AnimeByGenreQuery.Medium, RecyclerView.ViewHolder>(DiffGenreCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return TagViewHolder.create(parent, "Genre")
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)
        if(currentData != null){
            Log.d("GenreAdapter", "onBindViewHolder: $currentData")
            (holder as TagViewHolder).bindItemGenre(currentData)
       }
    }

}

class DiffGenreCallback : DiffUtil.ItemCallback<AnimeByGenreQuery.Medium>(){
    override fun areItemsTheSame(
        oldItem: AnimeByGenreQuery.Medium,
        newItem: AnimeByGenreQuery.Medium
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AnimeByGenreQuery.Medium,
        newItem: AnimeByGenreQuery.Medium
    ): Boolean {
        return oldItem == newItem
    }

}
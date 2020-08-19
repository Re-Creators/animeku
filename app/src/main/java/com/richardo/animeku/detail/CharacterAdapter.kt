package com.richardo.animeku.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.DetailAnimeQuery
import com.richardo.animeku.viewholder.CharacterViewHolder

class CharacterAdapter(private val type : String, private val from : String)
    : ListAdapter<DetailAnimeQuery.Edge, RecyclerView.ViewHolder>(CharacterDiffCallback(type)){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return CharacterViewHolder.create(parent, from, type)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)

        if (currentData != null){
            (holder as CharacterViewHolder).bindToDetail(currentData)

        }
    }
}


private class CharacterDiffCallback(val typeAdapter : String) : DiffUtil.ItemCallback<DetailAnimeQuery.Edge>(){
    override fun areItemsTheSame(
        oldItem: DetailAnimeQuery.Edge,
        newItem: DetailAnimeQuery.Edge
    ): Boolean {
        if (typeAdapter == "Character"){
            return oldItem.node?.id == newItem.node?.id
        }
        return oldItem.voiceActors?.get(0)?.id == newItem.voiceActors?.get(0)?.id
    }

    override fun areContentsTheSame(
        oldItem: DetailAnimeQuery.Edge,
        newItem: DetailAnimeQuery.Edge
    ): Boolean {
       return oldItem == newItem
    }

}
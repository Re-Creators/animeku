package com.richardo.animeku.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.CharacterListQuery
import com.richardo.animeku.viewholder.CharacterViewHolder

class PagingCharacterAdapter(private val from : String, private val profile : String)
    : PagingDataAdapter<CharacterListQuery.Character, RecyclerView.ViewHolder>(PagingCharacterDiff()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterViewHolder.create(parent, from, profile)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      val currentData = getItem(position)

        if(currentData != null) {
            (holder as CharacterViewHolder).bindToSearch(currentData)
        }
    }

}

class PagingCharacterDiff : DiffUtil.ItemCallback<CharacterListQuery.Character>(){
    override fun areItemsTheSame(
        oldItem: CharacterListQuery.Character,
        newItem: CharacterListQuery.Character
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharacterListQuery.Character,
        newItem: CharacterListQuery.Character
    ): Boolean {
        return newItem == oldItem
    }

}
package com.richardo.animeku.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.DetailAnimeQuery
import com.richardo.animeku.databinding.RelationItemBinding

class RelationAdapter : ListAdapter<DetailAnimeQuery.Edge1, RecyclerView.ViewHolder>(RelationDiff()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = RelationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)
        (holder as MyViewHolder).bind(currentData)
    }

    class MyViewHolder(private val binding : RelationItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : DetailAnimeQuery.Edge1){
            binding.apply {
                relation = data
                executePendingBindings()
            }
        }
    }

}

private class RelationDiff : DiffUtil.ItemCallback<DetailAnimeQuery.Edge1>(){
    override fun areItemsTheSame(
        oldItem: DetailAnimeQuery.Edge1,
        newItem: DetailAnimeQuery.Edge1
    ): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DetailAnimeQuery.Edge1,
        newItem: DetailAnimeQuery.Edge1
    ): Boolean {
        return oldItem == newItem
    }

}
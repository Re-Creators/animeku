package com.richardo.animeku.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.CharacterProfileQuery
import com.richardo.animeku.databinding.CharacterRoleItemBinding

class CharacterRoleAdapter : ListAdapter<CharacterProfileQuery.Edge, RecyclerView.ViewHolder>(CharacterRoleDiff()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = CharacterRoleItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)

        if (currentData != null){
            (holder as MyViewHolder).bind(currentData)
        }
    }



    class MyViewHolder(private val binding : CharacterRoleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.character?.node?.id?.let { id ->
                    navigateToDetail(id, it)
                }
            }
        }

        private fun navigateToDetail(id : Int, view : View){
            val action = ProfileFragmentDirections.actionVoiceActorFragmentToDetailFragment(id)
            view.findNavController().navigate(action)
        }

        fun bind(item : CharacterProfileQuery.Edge){
            binding.apply {
                character = item
                executePendingBindings()
            }
        }

    }
}


class CharacterRoleDiff : DiffUtil.ItemCallback<CharacterProfileQuery.Edge>(){
    override fun areItemsTheSame(
        oldItem: CharacterProfileQuery.Edge,
        newItem: CharacterProfileQuery.Edge
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharacterProfileQuery.Edge,
        newItem: CharacterProfileQuery.Edge
    ): Boolean {
        return newItem == oldItem
    }

}

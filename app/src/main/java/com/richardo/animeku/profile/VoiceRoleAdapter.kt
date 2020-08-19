package com.richardo.animeku.profile

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.VoiceActorsProfileQuery
import com.richardo.animeku.databinding.VoiceRoleItemBinding

class VoiceRoleAdapter : ListAdapter<VoiceActorsProfileQuery.Edge, RecyclerView.ViewHolder>(VoiceRoleDiff()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = VoiceRoleItemBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)

        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)

        if (currentData != null) {
            (holder as MyViewHolder).bind(currentData)
        }
    }

    class MyViewHolder(private val binding : VoiceRoleItemBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.apply {
                setAnimeClickListener {
                    this.voiceActor?.media?.get(0)?.id?.let { id ->
                        Log.d("VoiceRoleAdapter", "navigate")
                        navigateToDetail(id, it)
                    }
                }

                setCharacterClickListener {
                    this.voiceActor?.node?.id?.let {id ->
                        navigateToProfile(id, it)
                    }
                }
            }
        }

        private fun navigateToDetail(id : Int, view : View) {
            val action = ProfileFragmentDirections.actionVoiceActorFragmentToDetailFragment(id)
            view.findNavController().navigate(action)
        }

        private fun navigateToProfile(id :Int, view: View) {
            val action = ProfileFragmentDirections.actionVoiceActorFragmentSelf(id, "Character")
            view.findNavController().navigate(action)
        }

        fun bind(item : VoiceActorsProfileQuery.Edge?){
            binding.apply {
                voiceActor = item
                executePendingBindings()
            }
        }
    }


}

class VoiceRoleDiff : DiffUtil.ItemCallback<VoiceActorsProfileQuery.Edge>(){
    override fun areItemsTheSame(
        oldItem: VoiceActorsProfileQuery.Edge,
        newItem: VoiceActorsProfileQuery.Edge
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: VoiceActorsProfileQuery.Edge,
        newItem: VoiceActorsProfileQuery.Edge
    ): Boolean {
        return oldItem == newItem
    }

}
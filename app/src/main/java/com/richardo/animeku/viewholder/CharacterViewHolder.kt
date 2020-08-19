package com.richardo.animeku.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.CharacterListQuery
import com.example.animeku.DetailAnimeQuery
import com.richardo.animeku.data.Character
import com.richardo.animeku.databinding.CharacterItemBinding
import com.richardo.animeku.detail.DetailFragmentDirections
import com.richardo.animeku.search.SearchFragmentDirections

class CharacterViewHolder(
    private val binding : CharacterItemBinding,
    private val from : String,
    private val type : String
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.setClickListener {
            binding.data?.id?.let {id ->
                navigateToProfile(id, it)
            }
        }
    }

    private fun navigateToProfile(id : Int, view : View){
        when(from) {
            "Detail" -> {
                val action = DetailFragmentDirections.actionDetailFragmentToVoiceActorFragment(id, type)
                view.findNavController().navigate(action)
            }
            "Search" -> {
                val action = SearchFragmentDirections.actionSearchFragmentToVoiceActorFragment(id, type)
                view.findNavController().navigate(action)
            }
        }


    }

    fun bindToDetail(item : DetailAnimeQuery.Edge?){
        when (type){
            "Character" -> {
                binding.apply {
                    data = Character(
                        id = item?.node?.id,
                        imgUrl = item?.node?.image?.large,
                        name = item?.node?.name?.full,
                        role = item?.role.toString()
                    )
                    executePendingBindings()
                }

            }
            "Voice Actor" -> {
                item?.voiceActors?.let { list ->
                    if (list.isNotEmpty()) {
                        list[0]?.let { va ->
                            binding.apply {
                                data = Character(
                                    id = va.id,
                                    imgUrl = va.image?.large,
                                    name = va.name?.full,
                                    role = if (item.node?.name?.first == null || item.node.name.first == "") {
                                        item.node?.name?.full
                                    } else {
                                        item.node.name.first
                                    }
                                )
                                executePendingBindings()
                            }
                        }
                    }
                }
            }

        }
    }

    fun bindToSearch(character : CharacterListQuery.Character){
        binding.apply {
            data = Character(
                id = character.id,
                imgUrl = character.image?.large,
                name = character.name?.full
            )
            executePendingBindings()
        }

    }

    companion object {
        fun create(parent : ViewGroup, from : String, type: String) : CharacterViewHolder {
            val view = CharacterItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

            return CharacterViewHolder(view, from, type)
        }
    }
}
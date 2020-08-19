package com.richardo.animeku.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.OngoingListQuery
import com.richardo.animeku.databinding.OngoingItemBinding
import com.richardo.animeku.home.HomeFragmentDirections
import com.richardo.animeku.listAll.ListAllFragmentDirections

class OngoingViewHolder(private val binding : OngoingItemBinding, private val from: String)
    : RecyclerView.ViewHolder(binding.root){

    init {
        binding.setClickListener {
            binding.data?.let { data ->
                navigateToDetail(it, data.id)
            }
        }
    }

    fun bind(currentData : OngoingListQuery.Medium){
        binding.apply {
            data = currentData
            executePendingBindings()
        }
    }

    fun navigateToDetail(view : View, id : Int){
        when(from){
            "Home" -> {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id)
                view.findNavController().navigate(action)
            }
            "List All" -> {
                val action = ListAllFragmentDirections.actionListAllFragmentToDetailFragment(id)
                view.findNavController().navigate(action)

            }
        }
    }

    companion object{
        fun create(parent : ViewGroup, from: String) : OngoingViewHolder{
            val view = OngoingItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
            return OngoingViewHolder(view, from)
        }
    }
}
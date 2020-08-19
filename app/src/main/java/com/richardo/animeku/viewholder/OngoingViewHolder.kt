package com.richardo.animeku.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.OngoingListQuery
import com.richardo.animeku.databinding.OngoingItemBinding
import com.richardo.animeku.home.HomeFragmentDirections
import com.richardo.animeku.listAll.ListAllFragmentDirections
import com.richardo.animeku.utilities.Constants
import com.richardo.animeku.utilities.Utils

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

            if (from == Constants.HOME) {
                ongoingViewgroup.apply {
                    val params = ConstraintLayout.LayoutParams(
                        Utils.convertDpIntoPx(170f, this),
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )

                    params.marginStart = Utils.convertDpIntoPx(8f, this)
                    params.marginEnd = Utils.convertDpIntoPx(8f, this)

                    layoutParams = params
                }
            }


            data = currentData
            executePendingBindings()
        }
    }

    fun navigateToDetail(view : View, id : Int){
        when(from){
            Constants.HOME -> {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id)
                view.findNavController().navigate(action)
            }
            Constants.LIST_ALL -> {
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
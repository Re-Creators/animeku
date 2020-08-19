package com.richardo.animeku.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeku.SortListQuery
import com.richardo.animeku.R
import com.richardo.animeku.data.Tag
import com.richardo.animeku.databinding.TagItemBinding
import com.richardo.animeku.listAll.ListAllFragmentDirections
import com.richardo.animeku.search.SearchFragmentDirections
import kotlinx.android.synthetic.main.tag_item.view.*

class TagAdapter(val from : String)
    : ListAdapter<SortListQuery.Medium, RecyclerView.ViewHolder>(TagDiffCallback()) {

    var currentPage : Int? = null
    var onItemClick : ((Int) -> Unit)? = null
    var endOfListReached : ((Int?) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = TagItemBinding.inflate( LayoutInflater.from(parent.context),
        parent, false)
        return MyViewHolder(view, from)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = getItem(position)

        (holder as MyViewHolder).bindItem(currentData)


        if (position == itemCount - 1){
            endOfListReached?.invoke(currentPage)
        }

    }

    class MyViewHolder(private val binding: TagItemBinding, val from: String)
        : RecyclerView.ViewHolder(binding.root){

        init {
            binding.setClickListener {
                binding.tag?.let {tag ->
                    navigateToDetail(tag.id, it)
                }

            }
        }

        private fun navigateToDetail(id : Int, view : View){
            when(from){
                "home" -> {
                    view.findNavController().navigate(HomeFragmentDirections
                        .actionHomeFragmentToDetailFragment(id))
                }
                "listall" -> {
                    view.findNavController().navigate(ListAllFragmentDirections
                        .actionListAllFragmentToDetailFragment(id))
                }
                "search" -> {
                    view.findNavController().navigate(SearchFragmentDirections
                        .actionSearchFragmentToDetailFragment(id))
                }
            }
        }

        fun bindItem(currentData : SortListQuery.Medium){
            binding.apply {
                val data = Tag(
                    id = currentData.id,
                    imgUrl = currentData.coverImage?.extraLarge,
                    name = currentData.title?.romaji,
                    rating = currentData.averageScore
                )
                tag = data
                executePendingBindings()
            }
        }
    }


}

class TagDiffCallback : DiffUtil.ItemCallback<SortListQuery.Medium>(){
    override fun areItemsTheSame(
        oldItem: SortListQuery.Medium,
        newItem: SortListQuery.Medium
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SortListQuery.Medium,
        newItem: SortListQuery.Medium
    ): Boolean {
        return oldItem == newItem
    }

}
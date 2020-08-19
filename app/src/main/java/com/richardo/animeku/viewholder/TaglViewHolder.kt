package com.richardo.animeku.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.animeku.AnimeByGenreQuery
import com.example.animeku.DetailAnimeQuery
import com.example.animeku.SortListQuery
import com.example.animeku.UpcomingListQuery
import com.richardo.animeku.data.Tag
import com.richardo.animeku.databinding.TagItemBinding
import com.richardo.animeku.detail.DetailFragmentDirections
import com.richardo.animeku.genre.GenreFragmentDirections
import com.richardo.animeku.home.HomeFragmentDirections
import com.richardo.animeku.listAll.ListAllFragmentDirections
import com.richardo.animeku.search.SearchFragmentDirections

class TagViewHolder(
    private val binding: TagItemBinding,
    private val from : String) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.setClickListener {
            binding.tag?.let { tag ->
                navigateToDetail(it, tag.id)
            }
        }
    }

    fun bindItemTag(currentData : SortListQuery.Medium){
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

    fun bindItemUpcoming(currentData: UpcomingListQuery.Medium){
        binding.apply {
            val data = Tag(
                id = currentData.id,
                imgUrl = currentData.coverImage?.extraLarge,
                name = currentData.title?.romaji,
                rating = null
            )
            tag = data
            executePendingBindings()
        }
    }

    fun bindItemGenre(currentData: AnimeByGenreQuery.Medium){
        binding.apply {
            val data = Tag(
                id = currentData.id,
                imgUrl = currentData.coverImage?.extraLarge,
                name = currentData.title?.romaji,
                rating = null
            )
            tag = data
            executePendingBindings()
        }
    }

    fun bindItemRecommendation(currentData: DetailAnimeQuery.Node2){
        binding.apply {
            val data = Tag(
                id = currentData.mediaRecommendation!!.id,
                imgUrl = currentData.mediaRecommendation?.coverImage?.extraLarge,
                name = currentData.mediaRecommendation?.title?.romaji,
                rating = null
            )
            tag = data
            executePendingBindings()
        }
    }


    private fun navigateToDetail(view : View, id : Int){
        when(from){
            "List All" -> {
                val action =
                    ListAllFragmentDirections.actionListAllFragmentToDetailFragment(
                        id
                    )
                view.findNavController().navigate(action)
            }
            "Genre" -> {
                val action = GenreFragmentDirections.actionGenreFragmentToDetailFragment(id)
                view.findNavController().navigate(action)
            }
            "Search" -> {
                view.findNavController().navigate(
                    SearchFragmentDirections
                    .actionSearchFragmentToDetailFragment(id))
            }
            "Home" -> {
                view.findNavController().navigate(
                    HomeFragmentDirections
                    .actionHomeFragmentToDetailFragment(id))
            }
            "Detail" -> {
                view.findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentSelf(id)
                )
            }

        }

    }

    companion object{
        fun create(parent : ViewGroup, from : String) : TagViewHolder {
            val view = TagItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

            return TagViewHolder(view, from)
        }
    }
}
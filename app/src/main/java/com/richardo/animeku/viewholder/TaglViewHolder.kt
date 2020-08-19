package com.richardo.animeku.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.richardo.animeku.utilities.Constants
import com.richardo.animeku.utilities.Utils

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

            if (from == Constants.HOME) {
                viewgroupTag.apply {
                    val params = ConstraintLayout.LayoutParams(
                        Utils.convertDpIntoPx(170f, this),
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )

                    params.marginStart = Utils.convertDpIntoPx(8f, this)
                    params.marginEnd = Utils.convertDpIntoPx(8f, this)

                    layoutParams = params
                }
            }
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

            viewgroupTag.apply {
                val params = ConstraintLayout.LayoutParams(
                    Utils.convertDpIntoPx(170f, this),
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )

                params.marginStart = Utils.convertDpIntoPx(8f, this)
                params.marginEnd = Utils.convertDpIntoPx(8f, this)

                layoutParams = params
            }


            val data = Tag(
                id = currentData.mediaRecommendation!!.id,
                imgUrl = currentData.mediaRecommendation.coverImage?.extraLarge,
                name = currentData.mediaRecommendation.title?.romaji,
                rating = null
            )
            tag = data
            executePendingBindings()
        }
    }


    private fun navigateToDetail(view : View, id : Int){
        when(from){
            Constants.LIST_ALL -> {
                val action =
                    ListAllFragmentDirections.actionListAllFragmentToDetailFragment(
                        id
                    )
                view.findNavController().navigate(action)
            }
            Constants.GENRE -> {
                val action = GenreFragmentDirections.actionGenreFragmentToDetailFragment(id)
                view.findNavController().navigate(action)
            }
            Constants.SEARCH -> {
                view.findNavController().navigate(
                    SearchFragmentDirections
                    .actionSearchFragmentToDetailFragment(id))
            }
            Constants.HOME -> {
                view.findNavController().navigate(
                    HomeFragmentDirections
                    .actionHomeFragmentToDetailFragment(id))
            }
            Constants.DETAIL -> {
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
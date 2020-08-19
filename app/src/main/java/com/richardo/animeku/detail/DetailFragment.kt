package com.richardo.animeku.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.animeku.DetailAnimeQuery
//import com.richardo.animeku.DetailFragmentArgs
import com.richardo.animeku.R
import com.richardo.animeku.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.toolbar.*


class DetailFragment : Fragment() {

    private val args : DetailFragmentArgs by navArgs()
    private var TAG = "DetailFragment"
    private val viewModelDetail: DetailViewModel by viewModels {
        DetailViewModelFactory(args.animeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(inflater,R.layout.fragment_detail, container, false)
            .apply {
                viewmodel =  viewModelDetail
                lifecycleOwner = viewLifecycleOwner

                detailToolbar.setNavigationOnClickListener { view ->
                    view.findNavController().navigateUp()
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val charAdapter = CharacterAdapter("Character", "Detail")
        val voiceAdapter = CharacterAdapter("Voice Actor", "Detail")
        val relationAdapter = RelationAdapter()
        val recommendationAdapter = RecommendationAdapter()


        rv_characters.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = charAdapter
        }

        rv_voice_actors.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = voiceAdapter
        }

        rv_recommendations.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendationAdapter
        }

        rv_relation.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = relationAdapter
        }

        viewModelDetail.anime.observe(viewLifecycleOwner, Observer {detailAnime ->
            if (detailAnime.characters?.edges != null){
                // Melakukan filter pada data class voice actor
                val filteredVoiceActor = detailAnime.characters.edges.filter {edge ->
                    edge?.voiceActors != null && edge.voiceActors.isNotEmpty()
                }

                charAdapter.submitList(detailAnime.characters.edges)
                voiceAdapter.submitList(filteredVoiceActor)

                if (filteredVoiceActor.isEmpty()) {
                    tv_heading_voice_actor.visibility = View.GONE
                }
            }


            detailAnime.relations?.edges?.let {relation ->
                relationAdapter.submitList(relation)
            }
            detailAnime.recommendations?.nodes?.let {recom ->
                recommendationAdapter.submitList(recom)

            }
        })
    }

}
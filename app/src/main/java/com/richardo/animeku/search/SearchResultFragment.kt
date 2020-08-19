package com.richardo.animeku.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.richardo.animeku.GridSpacingItemDecoration
import com.richardo.animeku.R
import com.richardo.animeku.home.TagAdapter
import com.richardo.animeku.listAll.PagingTagAdapter
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [SearchResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@ExperimentalCoroutinesApi
class SearchResultFragment : Fragment() {

    private var param: String? = null
    private val viewModel: SearchViewModel by activityViewModels()
    private var job: Job? = null

    private lateinit var characterAdapter: PagingCharacterAdapter
    private lateinit var animeAdapter: PagingTagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM1)
            when (param) {
                "character" -> {
                    characterAdapter = PagingCharacterAdapter("Search", "Character")
                }
                "anime" -> {
                    animeAdapter = PagingTagAdapter("Search")
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (param) {
            "character" -> {
                rv_search_result.apply {
                    adapter = characterAdapter
                    addItemDecoration(GridSpacingItemDecoration(2, 50, true))
                }

                viewModel.keywords.observe(viewLifecycleOwner, Observer {
                    it.getContentIfNothandled(true)?.let { keyword ->
                        Log.d("SearchResult", "Character $keyword")
                        loadCharacterList(characterAdapter)
                    }
                })

                characterAdapter.addLoadStateListener { loadStates ->
                    if (characterAdapter.itemCount <= 0) {
                        rv_search_result.isVisible = loadStates.source.refresh is LoadState.NotLoading
                        pb_search_result.isVisible = loadStates.source.refresh is LoadState.Loading
                    }
                }

            }
            "anime" -> {
                //Mengatur adapter recyclerview
                rv_search_result.apply {
                    adapter = animeAdapter
                    addItemDecoration(
                        GridSpacingItemDecoration(
                            2,
                            50,
                            true
                        )
                    )
                }

                viewModel.keywords.observe(viewLifecycleOwner, Observer {
                    it.getContentIfNothandled()?.let {
                        loadAnimeList(animeAdapter)
                    }
                })

                animeAdapter.addLoadStateListener { loadStates ->
                    if (animeAdapter.itemCount <= 0) {
                        rv_search_result.isVisible = loadStates.source.refresh is LoadState.NotLoading
                        pb_search_result.isVisible = loadStates.source.refresh is LoadState.Loading
                    }
                }

            }

        }
    }

    private fun loadAnimeList(adapter: PagingTagAdapter) {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getAnimeList().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun loadCharacterList(adapter: PagingCharacterAdapter) {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getCharacterList().collectLatest {
                adapter.submitData(it)
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SearchResultFragment.
         */
        @JvmStatic
        fun newInstance(param : String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param)
                }
            }


    }
}
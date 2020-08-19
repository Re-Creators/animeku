package com.richardo.animeku.listAll

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.richardo.animeku.GridSpacingItemDecoration
import com.richardo.animeku.R
import kotlinx.android.synthetic.main.fragment_list_all.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ListAllFragment : Fragment() {

    private val args : ListAllFragmentArgs by navArgs()
    private var loadJob : Job? = null

    private lateinit var taguAdapter : PagingTagAdapter
    private lateinit var upcomingAdapter : ListAllUpcomingAdapter
    private lateinit var ongoingAdapter: PagingOngoingAdapter

    private val viewModel : ListAllViewModel by viewModels {
        ListAllViewModelFactory(args.tag)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when(args.tag){
            "Upcoming" -> {
                upcomingAdapter = ListAllUpcomingAdapter()
            }
            "Ongoing" -> {
                ongoingAdapter = PagingOngoingAdapter()
            }
            else -> {
                taguAdapter = PagingTagAdapter("List All")

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_list_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_list_all.apply {
            adapter = when(args.tag){
                "Upcoming" -> {
                    upcomingAdapter
                }
                "Ongoing" -> {
                    ongoingAdapter
                }
                else -> {
                    taguAdapter

                }
            }
            addItemDecoration(GridSpacingItemDecoration(2, 50, true))
        }
        load()
        addLoadStates()

    }

    private fun addLoadStates(){
        when(args.tag){
            "Upcoming" -> {
                upcomingAdapter.addLoadStateListener {loadStates ->
                    if (upcomingAdapter.itemCount <= 0) {
                        rv_list_all.isVisible = loadStates.source.refresh is LoadState.NotLoading
                        pb_listall.isVisible = loadStates.source.refresh is LoadState.Loading
                    }

                }
            }
            "Ongoing" -> {
                ongoingAdapter.addLoadStateListener {loadStates ->
                    if (ongoingAdapter.itemCount <= 0) {
                        rv_list_all.isVisible = loadStates.source.refresh is LoadState.NotLoading
                        pb_listall.isVisible = loadStates.source.refresh is LoadState.Loading
                    }
                }
            }
            else -> {
                taguAdapter.addLoadStateListener {loadStates ->
                    if (taguAdapter.itemCount <= 0) {
                        rv_list_all.isVisible = loadStates.source.refresh is LoadState.NotLoading
                        pb_listall.isVisible = loadStates.source.refresh is LoadState.Loading
                    }
                }
            }
        }

    }


    private fun load() {
        loadJob?.cancel()
        loadJob = lifecycleScope.launch {
            when(args.tag){
                "Upcoming" -> {
                    viewModel.getUpcoming().collectLatest {
                        upcomingAdapter.submitData(it)
                    }
                }
                "Ongoing" -> {
                   viewModel.getOngoing().collectLatest {
                       ongoingAdapter.submitData(it)
                   }
                }
                else -> {
                    viewModel.getAnime().collectLatest {
                        taguAdapter.submitData(it)
                    }

                }
            }

        }
    }

}


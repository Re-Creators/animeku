package com.richardo.animeku.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.richardo.animeku.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.abs
import kotlin.math.log


class HomeFragment : Fragment(){

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("HomFragment", "onCreateView Called")

        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("HomeFragment", "Created")

        // Adapter Recyclerview
        val popAdapter  = TagAdapter("home")
        popular_rv.adapter = popAdapter

        val trendAdapter = TagAdapter("home")
        trending_rv.adapter = trendAdapter

        val upcomingAdapter = UpcomingAdapter()

        val ongoingAdapter = OngoingAdapter("Home")
        ongoing_rv.adapter = ongoingAdapter


        //Menampilkan error dialog
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error){
                errorDialog()
            }
        })


        // Setting view pager upcoming
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer { page : View, position : Float ->
            val r : Float = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        view_pager_upcoming.apply {
            adapter = upcomingAdapter
            offscreenPageLimit = 9
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(compositePageTransformer)
        }


        upcomingAdapter.onItemClick = {id ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id)
            findNavController().navigate(action)
        }

        // Mengatur navigasi untuk ke fragment listall
        tv_view_all_popular.setOnClickListener {
            navigateToListAll(getString(R.string.most_popular))

        }

        tv_view_all_trending.setOnClickListener {
            navigateToListAll(getString(R.string.trending_now))
        }

        tv_view_all_upcoming.setOnClickListener {
            navigateToListAll("Upcoming")
        }

        tv_view_all_ongoing.setOnClickListener {
            navigateToListAll("Ongoing")
        }

        search_input.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus){
                val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                findNavController().navigate(action)
            }
        }

        //Memasukkan data kedalam recyclerview menegecek visibility view
        subscribeUiOngoing(ongoingAdapter)
        subscribeUiPopular(popAdapter)
        subscribeUiTrending(trendAdapter)
        subscribeUiUpcoming(upcomingAdapter)
    }

    private fun subscribeUiPopular(adapter: TagAdapter){
        viewModel.popularList.observe(viewLifecycleOwner, Observer { list ->
            if (list != null){
                adapter.submitList(list)
                shimmer_tag.visibility = View.GONE
                popular_rv.visibility = View.VISIBLE
            }
        })

    }

    private fun subscribeUiTrending(adapter: TagAdapter){
        // Menambahkan data ke adapter recylerview saat data selesai di ambil dari server
        viewModel.trendingList.observe(viewLifecycleOwner, Observer { list ->
            if (list != null){
                adapter.submitList(list)
                trending_rv.visibility = View.VISIBLE
            }
        })
    }

    private fun subscribeUiUpcoming(adapter: UpcomingAdapter){
        viewModel.upcomingList.observe(viewLifecycleOwner, Observer { list ->
            if(list != null){
                adapter.data = list
                shimmer_upcoming.visibility = View.GONE
                view_pager_upcoming.visibility = View.VISIBLE
            }

        })
    }

    private fun subscribeUiOngoing(adapter: OngoingAdapter){
        viewModel.ongoingList.observe(viewLifecycleOwner, Observer { list ->
            if (list != null){
                adapter.data = list
                ongoing_group.visibility = View.VISIBLE
            }
        })
    }

    private fun navigateToListAll(to : String){
        val action = HomeFragmentDirections.actionHomeFragmentToListAllFragment(to)
        findNavController().navigate(action)
    }

    private fun errorDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle("Koneksi bermasalah")
            .setMessage("Koneksi anda bermaslah pastikan internet anda terhubung")
            .setPositiveButton("TUTUP"){_,_ ->

            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        shimmer_tag.startShimmer()
        shimmer_upcoming.startShimmer()
    }

}
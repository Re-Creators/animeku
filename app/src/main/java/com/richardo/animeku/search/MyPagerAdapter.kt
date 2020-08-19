package com.richardo.animeku.search

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.richardo.animeku.search.SearchResultFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MyPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    private val data = arrayListOf("anime", "character")
    private val num_item = 2

    override fun getItemCount() = num_item

    @ExperimentalCoroutinesApi
    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {
                Log.d("MyPagerAdapter", "createFragment: Anime")
                return SearchResultFragment.newInstance("anime")

            }
            1 -> {
                Log.d("MyPagerAdapter", "createFragment: Character")
                return SearchResultFragment.newInstance("character")
            }
                else -> return SearchResultFragment.newInstance(data[position])

        }

    }

}
package com.richardo.animeku.search

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.richardo.animeku.R
import com.richardo.animeku.listAll.PagingTagAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private  val viewModel: SearchViewModel by activityViewModels()
    private var job : Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sv_search.requestFocus()

        sv_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

          override fun onQueryTextSubmit(query: String?): Boolean {
              if (!query.isNullOrEmpty()){
                  viewModel.submitKeyword(query)
                  viewModel.fetchCharacter()
              }

              return false
          }
      })


        val tabText = listOf("Anime", "Character")
        val viewPager = view.findViewById<ViewPager2>(R.id.viewpager)
        val pagerAdapter = MyPagerAdapter(this)

        viewPager.adapter = pagerAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.text = tabText[position]
        }.attach()


    }
}
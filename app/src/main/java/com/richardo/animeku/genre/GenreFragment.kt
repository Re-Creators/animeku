package com.richardo.animeku.genre

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.flexbox.FlexboxLayout
import com.richardo.animeku.GridSpacingItemDecoration
import com.richardo.animeku.R
import com.richardo.animeku.databinding.FragmentGenreBinding
import kotlinx.android.synthetic.main.fragment_genre.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class GenreFragment : Fragment() {

    private var checked = booleanArrayOf()
    private var genres = arrayOf<String>()
    private  val viewModel: GenreViewModel by viewModels()
    private val genreAdapter = GenreAdapter()
    private var loadJob : Job? = null

    private fun loadAnime(){
        loadJob?.cancel()
        loadJob = lifecycleScope.launch{
           viewModel.getAnime().collectLatest {
               genreAdapter.submitData(it)
           }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentGenreBinding.inflate(inflater, container, false)
        context ?: return binding.root


        binding.rvGenreanime.apply {
            adapter = genreAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 50, true))
        }

        // Mengatur visibility dari recyclerview dan progressbar
        genreAdapter.addLoadStateListener {loadStates ->
            if(genreAdapter.itemCount <= 0) {
                binding.rvGenreanime.isVisible = loadStates.source.refresh is LoadState.NotLoading
                binding.pbGenre.isVisible = loadStates.source.refresh is LoadState.Loading
            }
        }

        loadAnime()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.genreSelected.observe(viewLifecycleOwner, Observer { genreSelected ->
            if(genreSelected != null){

                if (genreSelected.isEmpty()){
                    viewModel.genreSelected.value = null
                }

                genre_selected_container.removeAllViews()
                img_genre_tag.visibility = View.VISIBLE
                generateTextviewGenre(genreSelected)
                loadAnime()
            }else{
                img_genre_tag.visibility = View.GONE
            }
        })

        viewModel.genreList.observe(viewLifecycleOwner, Observer { genreList ->
            if(genreList != null){
                val bool = mutableListOf<Boolean>()

                for (i in (0..genreList.size)){
                    bool.add(false)
                }

                checked = bool.toBooleanArray()

                genres = genreList.toTypedArray()
            }
        })

        viewModel.sortSelected.observe(viewLifecycleOwner, Observer { sort ->
            if (sort != null){
                tv_genre_sort_selected.text = getString(R.string.sort_selected, sort.toString()).split("_")[0]
                loadAnime()
            }else{
                tv_genre_sort_selected.text = getString(R.string.sort_selected, "POPULARITY")

            }

        })

        tv_genre_sort.setOnClickListener {
            sortingSelect()
        }
        tv_genre_choose.setOnClickListener {
            selectGenre()
        }

    }

    fun generateTextviewGenre(genreSelected : List<String>){
        for(genre in genreSelected){
            val tv = TextView(requireContext())
            val layoutParams  = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.bottomMargin = 8
            layoutParams.marginStart = 16
            tv.text = genre
            tv.layoutParams = layoutParams

            tv.setPadding(16,16,16,16)
            tv.setTextAppearance(requireContext(),R.style.genre_selected_text)
            tv.background = resources.getDrawable(R.drawable.bg_yellow)
            genre_selected_container.addView(tv)
        }
    }

    fun sortingSelect(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Sort By")
        val sort = arrayOf("Popularity", "Trending", "Score", "Episode")
        builder.setItems(sort){dialog, which ->
            dialog.dismiss()
            viewModel.sortSelected.value = viewModel.sorting[which]
            Toast.makeText(requireContext(), sort[which], Toast.LENGTH_SHORT).show()

        }
        builder.show()
    }

    fun selectGenre(){

        val selectedGenre = mutableListOf<String>()
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select your genre")

        builder.setMultiChoiceItems(genres, checked) { _, which, isChecked ->
            if(isChecked){
                checked[which] = true
            }
        }

        builder.setPositiveButton("OK"){_, _ ->
            checked.forEachIndexed{index: Int, s: Boolean ->
                if(s){
                    selectedGenre.add(genres[index])

                }
            }
            viewModel.genreSelected.value = selectedGenre
        }
        builder.show()
    }
}
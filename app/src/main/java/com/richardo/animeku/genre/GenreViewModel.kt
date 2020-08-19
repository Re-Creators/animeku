package com.richardo.animeku.genre

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.example.animeku.AnimeByGenreQuery
import com.example.animeku.GenreListQuery
import com.example.animeku.type.MediaSort
import com.richardo.animeku.apolloClient
import com.richardo.animeku.data.AniListRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class GenreViewModel : ViewModel(){

    var sortSelected = MutableLiveData<MediaSort>()
    var genreSelected = MutableLiveData<List<String>>()
    val genreList = MutableLiveData<List<String>>()
    var sorting = arrayOf(MediaSort.POPULARITY_DESC, MediaSort.TRENDING_DESC,
        MediaSort.SCORE_DESC, MediaSort.EPISODES_DESC)

    val anilist = AniListRepository()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        uiScope.launch {
            genreList.value = getGenreList()
        }

        genreSelected.value = null
        sortSelected.value = null

    }

    fun getAnime() : Flow<PagingData<AnimeByGenreQuery.Medium>>{
        return anilist.getResultAnimeByGenre(genreSelected.value, sortSelected.value)
            .cachedIn(viewModelScope)

    }

    private suspend fun getGenreList() : List<String>? {
        val genreList = try {
            requestGenreList()
        }catch (e : ApolloException){
            null
        }

        return genreList?.data?.genreCollection?.filterNotNull()
    }

    private suspend fun requestGenreList() : Response<GenreListQuery.Data>{
        return withContext(Dispatchers.IO){
            apolloClient.query(GenreListQuery()).toDeferred().await()
        }
    }

}
package com.richardo.animeku.listAll

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.animeku.OngoingListQuery
import com.example.animeku.SortListQuery
import com.example.animeku.UpcomingListQuery
import com.richardo.animeku.data.AniListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ListAllViewModel(private  val sortType : String) : ViewModel(){

    private val anilist = AniListRepository()
    val isVisible = MutableLiveData<Boolean>(false)


    fun getAnime() : Flow<PagingData<SortListQuery.Medium>>{
        return anilist.getResultSortList(sortType)
            .cachedIn(viewModelScope)

    }

    fun getUpcoming() : Flow<PagingData<UpcomingListQuery.Medium>>{
        return anilist.getResultUpcomingList()
            .cachedIn(viewModelScope)
    }

    fun getOngoing() : Flow<PagingData<OngoingListQuery.Medium>>{
        return anilist.getResultOngoingList()
            .map { pagingData ->
                pagingData.filter { data ->
                    data.nextAiringEpisode != null
                }
            }
            .cachedIn(viewModelScope)
    }

    override fun onCleared() {
        Log.d("ListAllViewModel", "Cleared")
    }
}
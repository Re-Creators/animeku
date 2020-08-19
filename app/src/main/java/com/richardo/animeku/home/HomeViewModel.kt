package com.richardo.animeku.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.example.animeku.OngoingListQuery
import com.example.animeku.SortListQuery
import com.example.animeku.UpcomingListQuery
import com.example.animeku.type.MediaSort
import com.richardo.animeku.apolloClient
import com.richardo.animeku.utilities.Utils
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : ViewModel(){

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var popularList = MutableLiveData<List<SortListQuery.Medium>>()
    var trendingList = MutableLiveData<List<SortListQuery.Medium>>()
    var upcomingList = MutableLiveData<List<UpcomingListQuery.Medium>>()
    var ongoingList = MutableLiveData<List<OngoingListQuery.Medium>>()

    var error = MutableLiveData<Boolean>()

    init {
        uiScope.launch {
            popularList.value = getPopular()
            trendingList.value = getTrending()
            upcomingList.value = getUpcoming()
            ongoingList.value = getOngoing()
        }
    }

    private suspend fun getPopular() : List<SortListQuery.Medium>? {
        val resPopular = try {
            requestPopularAnime()
        }catch (e : ApolloException){
            Log.d("HomeViewModel", e.message ?: "Error")
            error.value = true
            null
        }

        return resPopular?.data?.page?.media?.filterNotNull()
    }

    private suspend fun getTrending() : List<SortListQuery.Medium>? {
        val resPopular = try {
            requestTrendingAnime()
        }catch (e : ApolloException){
            Log.d("HomeViewModel", e.message ?: "Error")
            error.value = true
            null
        }

        return resPopular?.data?.page?.media?.filterNotNull()
    }

    private suspend fun getUpcoming() : List<UpcomingListQuery.Medium>? {
        val resUpcoming = try {
            requestUpcomingAnime()
        }catch (e : ApolloException){
            null
        }

        return resUpcoming?.data?.page?.media?.filterNotNull()
    }

    private suspend fun getOngoing() : List<OngoingListQuery.Medium>?{
        val resOngoing = try {
            requestOngoingAnime()
        }catch (e : ApolloException){
            Log.d("HomeViewModel", e.stackTrace.toString())
            null
        }

        return resOngoing?.data?.page?.media?.mapNotNull {
            if (it?.nextAiringEpisode != null) it else null
        }
    }

    private suspend fun requestPopularAnime() : Response<SortListQuery.Data>?{
        return withContext(Dispatchers.IO){
            apolloClient.query(SortListQuery(Input.fromNullable(1), Input.fromNullable(MediaSort.SCORE_DESC))).toDeferred().await()
        }
    }

    private suspend fun requestTrendingAnime() : Response<SortListQuery.Data>?{
        return withContext(Dispatchers.IO){
            apolloClient.query(SortListQuery(Input.fromNullable(1), Input.fromNullable(MediaSort.TRENDING_DESC))).toDeferred().await()
        }
    }

    private suspend fun requestUpcomingAnime() : Response<UpcomingListQuery.Data>{


        return withContext(Dispatchers.IO){
            apolloClient.query(UpcomingListQuery(Input.fromNullable(Utils.getFuzzyDateInt())))
                .toDeferred()
                .await()
        }
    }

    private suspend fun requestOngoingAnime() : Response<OngoingListQuery.Data>{
        return  withContext(Dispatchers.IO){
            apolloClient.query(OngoingListQuery()).toDeferred().await()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.d("HomeViewModel", "Cleared")
    }
}
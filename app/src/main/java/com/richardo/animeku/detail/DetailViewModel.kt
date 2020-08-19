package com.richardo.animeku.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.example.animeku.DetailAnimeQuery
import com.richardo.animeku.apolloClient
import kotlinx.coroutines.*
import java.text.DateFormatSymbols

class DetailViewModel(private val id : Int) : ViewModel(){

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var anime  = MutableLiveData<DetailAnimeQuery.Media>()
    var headingGone = true

    init {
        uiScope.launch {
            anime.value = getDetail()
        }
    }

    private suspend fun getDetail() : DetailAnimeQuery.Media?{
        val detail = try {
            requestDetail()
        }catch (e : ApolloException){
            Log.d("DetailViewModel", e.message ?: "Error")
            null
        }
        return detail?.data?.media
    }


    private suspend fun requestDetail() : Response<DetailAnimeQuery.Data>{
        return withContext(Dispatchers.IO){
            apolloClient.query(DetailAnimeQuery(Input.fromNullable(id))).toDeferred().await()
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
    }

    fun getRating(value : Int?) : Float{
        var rating = 0f
        if (value != null){
            rating = (value.toFloat() / 10f) / 2f
        }

        return rating
    }

    fun getEpisode(value: Int?) : String{
        if(value != null){
            return value.toString()
        }

        return "Unknown"
    }

    fun getStatus(value : String) : String{
        return value.toLowerCase().capitalize()
            .replace("_", " ")

    }

    fun getStartDate(value : DetailAnimeQuery.StartDate?) : String{

        if(value?.month != null){
            return "${convertMonth(value.month)} ${value.day}, ${value.year}"
        }

        return "Unknown"

    }

    private fun convertMonth(month : Int) : String{
        return DateFormatSymbols().months[month - 1]
    }
}
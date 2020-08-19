package com.richardo.animeku.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.example.animeku.CharacterListQuery
import com.example.animeku.SortListQuery
import com.richardo.animeku.Event
import com.richardo.animeku.apolloClient
import com.richardo.animeku.data.AniListRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class SearchViewModel : ViewModel(){

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val anime = MutableLiveData<List<SortListQuery.Medium>>()
    val character = MutableLiveData<List<CharacterListQuery.Character>>()


    private val anilist = AniListRepository()

    private val _keyword = MutableLiveData<Event<String>>()
    val keywords : LiveData<Event<String>>
        get() = _keyword

    fun submitKeyword(keyword : String){
        _keyword.value = Event(keyword)
    }

    fun fetchCharacter(){
        uiScope.launch {
            character.value = getCharacter()
        }
    }


    private suspend fun getCharacter() : List<CharacterListQuery.Character>? {
        val res = try {
            requestCharacter()
        }catch (e : ApolloException){
            null
        }

        return res?.data?.page?.characters?.filterNotNull()
    }



    private suspend fun requestCharacter() : Response<CharacterListQuery.Data>{
        return withContext(Dispatchers.IO){
            apolloClient.query(CharacterListQuery(Input.fromNullable(_keyword.value?.peekContent())))
                .toDeferred()
                .await()
        }
    }

    fun getAnimeList() : Flow<PagingData<SortListQuery.Medium>>{
        return anilist.getResultSortList(keyword = _keyword.value?.peekContent())
            .cachedIn(viewModelScope)
    }

    fun getCharacterList() : Flow<PagingData<CharacterListQuery.Character>>{
        return anilist.getResultCharacterList(_keyword.value?.peekContent())
            .cachedIn(viewModelScope)
    }

    override fun onCleared() {
        viewModelJob.cancel()
    }
}
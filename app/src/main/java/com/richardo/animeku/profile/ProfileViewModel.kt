package com.richardo.animeku.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.example.animeku.CharacterProfileQuery
import com.example.animeku.VoiceActorsProfileQuery
import com.richardo.animeku.apolloClient
import com.richardo.animeku.data.Profile
import kotlinx.coroutines.*

class ProfileViewModel(private val id : Int, private val opt : String) : ViewModel(){

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val voiceActor : MutableLiveData<VoiceActorsProfileQuery.Staff> = MutableLiveData()
    val character : MutableLiveData<CharacterProfileQuery.Character> = MutableLiveData()


    var profile : LiveData<Profile> = setProfile()

    init {
        uiScope.launch {
            when(opt){
                "Character" -> character.value = getCharacter()
                "Voice Actor" -> voiceActor.value = getVoiceActors()
            }
        }

        setProfile()
    }

    private fun setProfile() : LiveData<Profile>{
        return if (opt == "Character"){
                Transformations.map(character){character ->
                    Profile(
                        imgUrl = character?.image?.large,
                        fav = character?.favourites,
                        name = character?.name?.full,
                        alterName = character?.name?.native_,
                        desc = character?.description
                    )
                }
            }else {
                Transformations.map(voiceActor){voiceActor ->
                    Profile(
                        imgUrl = voiceActor?.image?.large,
                        fav = voiceActor?.favourites,
                        name = voiceActor?.name?.full,
                        alterName = voiceActor?.name?.native_,
                        desc = voiceActor?.description
                    )
                }
        }
    }


    private suspend fun getVoiceActors() : VoiceActorsProfileQuery.Staff?{
        val voiceActor = try {
            requestVoiceActors()
        }catch (e : ApolloException){
            Log.d("VoiceActorViewModel", e.message ?: "Error")
            null
        }

        return voiceActor?.data?.staff
    }

    private suspend fun getCharacter() : CharacterProfileQuery.Character? {
        val character = try {
            requestCharacter()
        }catch (e : ApolloException){
            null
        }

        return character?.data?.character
    }
    private suspend fun requestVoiceActors() : Response<VoiceActorsProfileQuery.Data>{
        return withContext(Dispatchers.IO){
            apolloClient.query(VoiceActorsProfileQuery(Input.fromNullable(id))).toDeferred().await()
        }
    }

    private suspend fun requestCharacter() : Response<CharacterProfileQuery.Data>{
        return withContext(Dispatchers.IO){
            apolloClient.query(CharacterProfileQuery(Input.fromNullable(id)))
                .toDeferred()
                .await()
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
    }
}
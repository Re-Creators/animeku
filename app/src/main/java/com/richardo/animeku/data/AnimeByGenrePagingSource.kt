package com.richardo.animeku.data

import android.util.Log
import androidx.paging.PagingSource
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.example.animeku.AnimeByGenreQuery
import com.example.animeku.type.MediaSort
import com.richardo.animeku.apolloClient


private const val STARTING_PAGE_INDEX = 1

class AnimeByGenrePagingSource(
    private val genre : List<String>?,
    private val sortBy : MediaSort?) : PagingSource<Int, AnimeByGenreQuery.Medium>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeByGenreQuery.Medium> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try{
            val response = apolloClient.query(AnimeByGenreQuery(
                Input.fromNullable(genre ?: listOf("Action")),
                Input.fromNullable(sortBy ?: MediaSort.POPULARITY_DESC),
                page = Input.fromNullable(page),
                perPage = Input.fromNullable(params.loadSize)
            )).toDeferred().await()

            val anime = response.data?.page?.media?.filterNotNull()
            Log.d("Paging", "load : $anime")

            LoadResult.Page(
                data = anime!!,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.data?.page?.pageInfo?.lastPage) null else page + 1
            )
        }catch (exception : Exception){
            Log.d("Error", "load : $exception")
            LoadResult.Error(exception)
        }

    }

}
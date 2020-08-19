package com.richardo.animeku.data

import androidx.paging.PagingSource
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.example.animeku.CharacterListQuery
import com.richardo.animeku.apolloClient

private const val STARTING_PAGE_INDEX = 1

class CharacterListPagingSource(
    private val keyword : String?
) : PagingSource<Int, CharacterListQuery.Character>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterListQuery.Character> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apolloClient.query(CharacterListQuery(
                keyword = Input.fromNullable(keyword),
                page = Input.fromNullable(page),
                perPage = Input.fromNullable(params.loadSize)
            )).toDeferred().await()

            val anime = response.data?.page?.characters?.filterNotNull()
            LoadResult.Page(
                data = anime!!,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.data?.page?.pageInfo?.lastPage) null else page + 1
            )
        }catch (exception : Exception) {
            LoadResult.Error(exception)
        }

    }

}
package com.richardo.animeku.data

import androidx.paging.PagingSource
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.example.animeku.UpcomingListQuery
import com.richardo.animeku.apolloClient
import com.richardo.animeku.utilities.Utils

private const val STARTING_PAGE_INDEX = 1

class UpcomingListPagingSource : PagingSource<Int, UpcomingListQuery.Medium>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UpcomingListQuery.Medium> {
        val page = params.key ?: STARTING_PAGE_INDEX
//
        return try {
            val response = apolloClient
                .query(UpcomingListQuery(
                    Input.fromNullable(Utils.getFuzzyDateInt()),
                    Input.fromNullable(page),
                    Input.fromNullable(params.loadSize)
                ))
                .toDeferred()
                .await()
            val anime = response.data?.page?.media?.filterNotNull()
            LoadResult.Page(
                data = anime!!,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.data?.page?.pageInfo?.lastPage) null else page + 1
            )
        }catch (exception : Exception){
            LoadResult.Error(exception)
        }
    }

}
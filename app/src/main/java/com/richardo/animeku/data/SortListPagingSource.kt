package com.richardo.animeku.data

import androidx.paging.PagingSource
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.example.animeku.SortListQuery
import com.example.animeku.type.MediaSort
import com.richardo.animeku.apolloClient


private const val STARTING_PAGE_INDEX = 1

class SortListPagingSource (
    private val sortBy : MediaSort,
    private val keyword : String? = null
) : PagingSource<Int, SortListQuery.Medium>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SortListQuery.Medium> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apolloClient
                .query(SortListQuery(
                    page = Input.fromNullable(page),
                    sortType = Input.fromNullable(sortBy),
                    perPage = Input.fromNullable(params.loadSize),
                    keyword = Input.fromNullable(keyword)
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
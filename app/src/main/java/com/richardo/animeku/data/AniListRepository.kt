package com.richardo.animeku.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animeku.*
import com.example.animeku.type.MediaSort
import kotlinx.coroutines.flow.Flow

class AniListRepository {
    fun getResultSortList(sortyBy : String = "other",
    keyword : String? = null) : Flow<PagingData<SortListQuery.Medium>>{
        return when (sortyBy){
            "Most popular" -> {
                Pager(
                    config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
                    pagingSourceFactory = {SortListPagingSource(MediaSort.SCORE_DESC)}
                ).flow
            }
            "Trending now" -> {
                Pager(
                    config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
                    pagingSourceFactory = {SortListPagingSource(MediaSort.TRENDING_DESC)}
                ).flow
            }
            else ->  Pager(
                config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
                pagingSourceFactory = {SortListPagingSource(MediaSort.POPULARITY_DESC, keyword)}
            ).flow
        }
    }

    fun getResultUpcomingList() : Flow<PagingData<UpcomingListQuery.Medium>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {UpcomingListPagingSource()}
        ).flow
    }

    fun getResultOngoingList() : Flow<PagingData<OngoingListQuery.Medium>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {OngoingListPagingSource()}
        ).flow
    }

    fun getResultAnimeByGenre(
        genre : List<String>?,
        sortBy : MediaSort?) : Flow<PagingData<AnimeByGenreQuery.Medium>>{

        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {AnimeByGenrePagingSource(genre, sortBy)}
        ).flow
    }

    fun getResultCharacterList(
        keyword: String?
    ) : Flow<PagingData<CharacterListQuery.Character>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {CharacterListPagingSource(keyword)}
        ).flow
    }

    companion object{
        private const val NETWORK_PAGE_SIZE = 25
    }
}
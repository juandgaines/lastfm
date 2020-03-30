package com.example.lasftfm.repository

import androidx.paging.LivePagedListBuilder
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.LastFmService
import kotlinx.coroutines.CoroutineScope

class LastFmRepo(
    private val service: LastFmService,
    private val database: LastFmDatabase) {

    fun fetch(couroutineScope: CoroutineScope, query: String):TrackResults{

        val dataSourceFactory=database.lastFmDao().listOfTracks(query = query)
        val boundaryCallback=TrackBoundaryCallback(service,database,couroutineScope)
        val networkErrors=boundaryCallback.networkErrors
        val data=LivePagedListBuilder(dataSourceFactory,DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return TrackResults(data,networkErrors)

    }

    fun fetchArtist(couroutineScope: CoroutineScope, query: String):ArtistsResults{

        val dataSourceFactory=database.lastFmDao().listOfArtist(query = query)
        val boundaryCallback=ArtistBoundaryCallback(service,database,couroutineScope)
        val networkErrors=boundaryCallback.networkErrors
        val data=LivePagedListBuilder(dataSourceFactory,DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return ArtistsResults(data,networkErrors)

    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}
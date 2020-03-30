package com.example.lasftfm.repository

import androidx.paging.LivePagedListBuilder
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.LastFmService
import com.example.lasftfm.network.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LastFmRepo(
    private val service: LastFmService,
    private val database: LastFmDatabase) {

    fun fetch(couroutineScope: CoroutineScope):TrackResults{

        val dataSourceFactory=database.lastFmDao().listOfTracks()
        val boundaryCallback=TrackBoundaryCallback(service,database,couroutineScope)
        val networkErrors=boundaryCallback.networkErrors
        val data=LivePagedListBuilder(dataSourceFactory,DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return TrackResults(data,networkErrors)

    }

    fun fetchArtist(couroutineScope: CoroutineScope):ArtistsResults{

        val dataSourceFactory=database.lastFmDao().listOfArtist()
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
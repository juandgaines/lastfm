package com.example.lasftfm.repository

import android.app.Application
import androidx.paging.LivePagedListBuilder
import com.example.lasftfm.repository.db.LastFmCache
import com.example.lasftfm.repository.db.LastFmDatabase
import com.example.lasftfm.repository.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class LastFmRepo(
    private val service: LastFmDataSource,
    private val database: LastFmDataSource

) : RepoOperations {

    private var viewModelJob: Job = Job()
    private var couroutineScope=CoroutineScope(viewModelJob + Dispatchers.IO)

    init {

        database.setCoroutine(couroutineScope)
    }


    override fun fetch(query: String): TrackResults {

        val dataSourceFactory = database.getAllTracks(query = query)
        val boundaryCallback = TrackBoundaryCallback(service, database, couroutineScope)
        val networkErrors = boundaryCallback.networkErrors
        val data = LivePagedListBuilder(dataSourceFactory!!, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return TrackResults(data, networkErrors)

    }

    override fun fetchArtist(query: String): ArtistsResults {

        val dataSourceFactory = database.getAllArtist(query = query)
        val boundaryCallback = ArtistBoundaryCallback(service, database, couroutineScope)
        val networkErrors = boundaryCallback.networkErrors
        val data = LivePagedListBuilder(dataSourceFactory!!, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return ArtistsResults(data, networkErrors)

    }

    override fun disposeJob() {
        viewModelJob.cancel()
    }

    companion object {

        @Volatile
        private var INSTANCE: LastFmRepo? = null

        fun getRepository(app: Application): LastFmRepo {
            return INSTANCE ?: synchronized(this) {

                LastFmRepo(
                    Network.getNetworkProvider(),
                    LastFmCache(LastFmDatabase.getInstance(app))
                )

            }
        }

        private const val DATABASE_PAGE_SIZE = 20
    }
}
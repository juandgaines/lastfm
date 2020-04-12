package com.example.lasftfm.db

import androidx.paging.DataSource
import com.example.lasftfm.network.Artist2
import com.example.lasftfm.network.LastFmService
import com.example.lasftfm.network.Track
import com.example.lasftfm.repository.LastFmDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LastFmCache(
    private val database: LastFmDatabase

) : LastFmDataSource {

    private lateinit var coroutineScope: CoroutineScope

    override fun insertTracks(posts: List<Track>) {
        coroutineScope.launch {
            database.lastFmDao().insert(posts)
        }
    }

    override fun getAllTracks(query: String): DataSource.Factory<Int, Track>? = database.lastFmDao().listOfTracks(query)

    override fun insertArtist(posts: List<Artist2>) {
        coroutineScope.launch {
            database.lastFmDao().insertArtist(posts)
        }
    }

    override fun getAllArtist(query: String): DataSource.Factory<Int, Artist2> =
        database.lastFmDao().listOfArtist(query)

    override fun deleteAllArtists() {
        coroutineScope.launch {
            database.lastFmDao().clearArtist()
        }
    }

    override fun deleteAllTracks() {
        coroutineScope.launch {
            database.lastFmDao().clearTracks()
        }
    }

    override suspend fun searchArtistsByQuery(
        page: Int,
        onSuccess: (artists: List<Artist2>) -> Unit,
        onError: (error: String) -> Unit
    ) =Unit
    override suspend fun searchTrackByQuery(
        page: Int,
        onSuccess: (artists: List<Track>) -> Unit,
        onError: (error: String) -> Unit
    ) =Unit

    override fun setCoroutine(scope: CoroutineScope) {
        coroutineScope=scope
    }

}
package com.example.lasftfm.repository

import androidx.paging.DataSource
import com.example.lasftfm.network.Artist2
import com.example.lasftfm.network.LastFmService
import com.example.lasftfm.network.Track
import kotlinx.coroutines.CoroutineScope

interface LastFmDataSource {
    fun insertTracks(posts: List<Track>)

    fun getAllTracks(query: String): DataSource.Factory<Int, Track>?

    fun insertArtist(posts: List<Artist2>)

    fun getAllArtist(query: String): DataSource.Factory<Int, Artist2>?

    fun deleteAllArtists()

    fun deleteAllTracks()

    suspend fun searchArtistsByQuery(
        page: Int,
        onSuccess: (artists: List<Artist2>) -> Unit,
        onError: (error: String) -> Unit
    )

    suspend fun searchTrackByQuery(
        page: Int,
        onSuccess: (artists: List<Track>) -> Unit,
        onError: (error: String) -> Unit
    )

    fun setCoroutine(coroutineScope: CoroutineScope)
}
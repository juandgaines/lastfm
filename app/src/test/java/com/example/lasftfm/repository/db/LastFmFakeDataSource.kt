package com.example.lasftfm.repository.db

import androidx.paging.DataSource
import com.example.lasftfm.repository.LastFmDataSource
import com.example.lasftfm.repository.network.Artist2
import com.example.lasftfm.repository.network.Track
import kotlinx.coroutines.CoroutineScope

class LastFmFakeDataSource(
    var listArtists: MutableList<Artist2>,
    var listTrack: MutableList<Track>
) : LastFmDataSource {

    private lateinit var coroutineScope: CoroutineScope

    override fun insertTracks(posts: List<Track>) {
        listTrack.addAll(posts)
    }

    override fun getAllTracks(query: String): DataSource.Factory<Int, Track>? {
        return ListDataSource(listTrack)
    }

    override fun insertArtist(posts: List<Artist2>) {
        listArtists.addAll(posts)
    }

    override fun getAllArtist(query: String): DataSource.Factory<Int, Artist2>? {
        return ListDataSource(listArtists)
    }

    override fun deleteAllArtists() {
        listArtists.clear()
    }

    override fun deleteAllTracks() {
        listTrack.clear()
    }

    override suspend fun searchArtistsByQuery(
        page: Int,
        onSuccess: (artists: List<Artist2>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        page
    }

    override suspend fun searchTrackByQuery(
        page: Int,
        onSuccess: (artists: List<Track>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        page
    }

    override fun setCoroutine(scope: CoroutineScope) {
        coroutineScope = scope
    }
}
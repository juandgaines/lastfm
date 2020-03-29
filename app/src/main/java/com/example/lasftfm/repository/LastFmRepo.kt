package com.example.lasftfm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.Network
import com.example.lasftfm.network.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LastFmRepo(private val database: LastFmDatabase) {

    val tracks: LiveData<List<Track>>
        get() = database.lastFmDao().listOfTracks()

    suspend fun refreshVideos() {
        try {
            val playList = Network.lastFm.getTracksList("spain", 1).tracks
            insert(playList.track)
        } catch (e: Throwable) {
            Log.e(LastFmRepo::class.java.simpleName, "${e.message}")
        }
    }

    private suspend fun insert(tracks: List<Track>) {
        return withContext(Dispatchers.IO) {
            database.lastFmDao().insert(tracks)
        }
    }
}
package com.example.lasftfm.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.lasftfm.db.LastFmDao
import com.example.lasftfm.network.Network
import com.example.lasftfm.network.Track
import kotlinx.coroutines.*

class ListLastFmViewModel(val database: LastFmDao, application: Application) :
    AndroidViewModel(application) {


    private var viewModelJob = Job()
    private val couroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    init {


    }

    fun fetchTracks() {
        couroutineScope.launch {
            try {
                var getTracksDeferred = Network.lastFm.getTracksList("spain", 1)
                insertTracks(getTracksDeferred.tracks.track)

            } catch (e: Throwable) {
                Log.e("MainActivity", "${e.message}")
            }
        }
    }

    private fun insertTracks(tracks: List<Track>) {
        couroutineScope.launch {
            insert(tracks)
        }
    }

    fun getTracks(): LiveData<List<Track>> {
             return database.listOfTracks()
    }

    private suspend fun insert(tracks: List<Track>) {
        return withContext(Dispatchers.IO) {
            database.insert(tracks)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class LastFmViewModelFactory(
    private val dataSource: LastFmDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListLastFmViewModel::class.java)) {
            return ListLastFmViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
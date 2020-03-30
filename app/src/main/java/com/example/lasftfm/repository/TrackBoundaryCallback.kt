package com.example.lasftfm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.LastFmService
import com.example.lasftfm.network.Track
import com.example.lasftfm.network.searchTracks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TrackBoundaryCallback(
    private val service: LastFmService,
    private val cache: LastFmDatabase,
    private val coroutineScope: CoroutineScope
) : PagedList.BoundaryCallback<Track>() {

    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    val networkErrors: LiveData<String>
        get() = _networkErrors

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        Log.d("RepoBoundaryCallback", "onZeroItemsLoaded")
        coroutineScope.launch {
            requestAndSaveData()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Track) {
        Log.d("RepoBoundaryCallback", "onItemAtEndLoaded")
        coroutineScope.launch {
            requestAndSaveData()
        }
    }

    private suspend fun requestAndSaveData() {
        if (isRequestInProgress) return
        isRequestInProgress = true
       searchTracks(service,
            lastRequestedPage,
            { repos ->
                cache.lastFmDao().insert(repos)
                lastRequestedPage++
                isRequestInProgress = false

            },
            { error ->
                _networkErrors.postValue(error)
                isRequestInProgress = false
            })
    }
}
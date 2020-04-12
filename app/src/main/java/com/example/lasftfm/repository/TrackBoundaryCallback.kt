package com.example.lasftfm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.lasftfm.network.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class TrackBoundaryCallback(
    private val service: LastFmDataSource,
    private val cache: LastFmDataSource,
    private val coroutineScope: CoroutineScope
) : PagedList.BoundaryCallback<Track>() {

    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    val networkErrors: LiveData<String>
        get() = _networkErrors

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
       Timber.tag("RepoBoundaryCallback").d("onZeroItemsLoaded")
        coroutineScope.launch {
            requestAndSaveData()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Track) {
        Timber.tag("RepoBoundaryCallback").d("onItemAtEndLoaded")
        coroutineScope.launch {
            requestAndSaveData()
        }
    }

    private suspend fun requestAndSaveData() {
        if (isRequestInProgress) return
        isRequestInProgress = true
        service.searchTrackByQuery(
            lastRequestedPage,
            { repos ->
                cache.insertTracks(repos)
                lastRequestedPage++
                isRequestInProgress = false

            },
            { error ->
                _networkErrors.postValue(error)
                isRequestInProgress = false
            })
    }
}
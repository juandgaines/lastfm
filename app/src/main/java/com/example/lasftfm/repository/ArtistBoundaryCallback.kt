package com.example.lasftfm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class ArtistBoundaryCallback(
    private val service: LastFmService,
    private val cache: LastFmDatabase,
    private val coroutineScope: CoroutineScope
) : PagedList.BoundaryCallback<Artist2>() {

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

    override fun onItemAtEndLoaded(itemAtEnd: Artist2) {
        Timber.tag("RepoBoundaryCallback").d("onItemAtEndLoaded")
        coroutineScope.launch {
            requestAndSaveData()
        }
    }

    private suspend fun requestAndSaveData() {
        if (isRequestInProgress) return
        isRequestInProgress = true
       searchArtists(service,
            lastRequestedPage,
            { repos ->
                cache.lastFmDao().insertArtist(repos)
                lastRequestedPage++
                isRequestInProgress = false

            },
            { error ->
                _networkErrors.postValue(error)
                isRequestInProgress = false
            })
    }
}
package com.example.lasftfm.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.lasftfm.db.LastFmDao
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.Network
import com.example.lasftfm.network.Track
import com.example.lasftfm.repository.LastFmRepo
import kotlinx.coroutines.*

class ListLastFmViewModel(application: Application) :
    AndroidViewModel(application) {


    private val database=LastFmDatabase.getInstance(application)
    private val lastFmRepo=LastFmRepo(database)

    private var viewModelJob = Job()
    private val couroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    val tracks=lastFmRepo.tracks

    init {

        couroutineScope.launch {
            lastFmRepo.refreshVideos()
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class LastFmViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListLastFmViewModel::class.java)) {
            return ListLastFmViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
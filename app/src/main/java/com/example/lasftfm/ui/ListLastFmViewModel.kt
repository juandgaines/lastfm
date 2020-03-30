package com.example.lasftfm.ui

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.lasftfm.network.Artist2
import com.example.lasftfm.network.Track
import com.example.lasftfm.repository.ArtistsResults
import com.example.lasftfm.repository.LastFmRepo
import com.example.lasftfm.repository.TrackResults
import kotlinx.coroutines.*

class ListLastFmViewModel(private val repository: LastFmRepo) :
    ViewModel() {

    private var viewModelJob = Job()
    private val couroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    private val _trackResult = MutableLiveData<TrackResults>()
    private val _artistResult = MutableLiveData<ArtistsResults>()

    val tracks: LiveData<PagedList<Track>> =
        Transformations.switchMap(_trackResult) { it -> it.data }
    val networkErrors: LiveData<String> =
        Transformations.switchMap(_trackResult) { it ->
        it.networkErrors
    }

    val artists: LiveData<PagedList<Artist2>> =
        Transformations.switchMap(_artistResult) { it -> it.data }
    val networkErrorsArtist: LiveData<String> =
        Transformations.switchMap(_artistResult) { it ->
            it.networkErrors
        }

    init {
        _trackResult.value = repository.fetch(couroutineScope)
        _artistResult.value=repository.fetchArtist(couroutineScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class LastFmViewModelFactory(
    private val repository: LastFmRepo
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListLastFmViewModel::class.java)) {
            return ListLastFmViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
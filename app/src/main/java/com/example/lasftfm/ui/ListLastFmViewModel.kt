package com.example.lasftfm.ui

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.lasftfm.network.Track
import com.example.lasftfm.repository.LastFmRepo
import com.example.lasftfm.repository.TrackResults
import kotlinx.coroutines.*

class ListLastFmViewModel(private val repository: LastFmRepo) :
    ViewModel() {

    private var viewModelJob = Job()
    private val couroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    private val _trackResult = MutableLiveData<TrackResults>()
    val tracks: LiveData<PagedList<Track>> =
        Transformations.switchMap(_trackResult) { it -> it.data }
    val networkErrors: LiveData<String> =
        Transformations.switchMap(_trackResult) { it ->
        it.networkErrors
    }

    init {
        _trackResult.value = repository.fetch(couroutineScope)
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
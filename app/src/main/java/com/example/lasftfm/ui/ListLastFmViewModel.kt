package com.example.lasftfm.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.lasftfm.Utils
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.Artist2
import com.example.lasftfm.network.LastFmService
import com.example.lasftfm.network.Network
import com.example.lasftfm.network.Track
import com.example.lasftfm.repository.ArtistsResults
import com.example.lasftfm.repository.LastFmRepo
import com.example.lasftfm.repository.TrackResults
import kotlinx.coroutines.*

class ListLastFmViewModel(private val context: Context) :
    ViewModel() {

    private lateinit var repository:LastFmRepo
    private var viewModelJob = Job()
    private val couroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    private val _trackResult = MutableLiveData<TrackResults>()
    private val _artistResult = MutableLiveData<ArtistsResults>()
    var queryLiveDataTracks = ""
    var queryLiveDataArtists = ""
    private var _selectedTrackLiveData = MutableLiveData<Track>()
    private var _selectedArtistLiveData = MutableLiveData<Artist2>()


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

    val selectedTrackLiveData: LiveData<Track>
        get() = _selectedTrackLiveData
    val selectedArtistLiveData: LiveData<Artist2>
        get() = _selectedArtistLiveData

    init {

        repository= LastFmRepo(Network.lastFm, LastFmDatabase.getInstance(context))
        fetchTracks("%")
        fetchArtists("%")
    }

    fun fetchTracks(query: String?) {
        _trackResult.value = repository.fetch(couroutineScope, convertToQueryForDb(query))
    }

    private fun convertToQueryForDb(query: String?): String {
        return Utils.convertToQuery(query)
    }

    fun fetchArtists(query: String?) {
        _artistResult.value = repository.fetchArtist(couroutineScope, convertToQueryForDb(query))
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun setTrackSelected(track:Track?){
        _selectedTrackLiveData.value=track

    }
    fun setArtistSelected(artist:Artist2?){
        _selectedArtistLiveData.value=artist
    }

}

class LastFmViewModelFactory(
    private val application: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListLastFmViewModel::class.java)) {
            return ListLastFmViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
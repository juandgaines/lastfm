package com.example.lasftfm.ui

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.lasftfm.Utils
import com.example.lasftfm.repository.network.Artist2
import com.example.lasftfm.repository.network.Track
import com.example.lasftfm.repository.ArtistsResults
import com.example.lasftfm.repository.LastFmRepo
import com.example.lasftfm.repository.TrackResults

class ListLastFmViewModel(private val repository: LastFmRepo) :
    ViewModel() {

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
        fetchTracks("%")
        fetchArtists("%")
    }

    fun fetchTracks(query: String?) {
        _trackResult.value = repository.fetch(convertToQueryForDb(query))
    }

    private fun convertToQueryForDb(query: String?): String {
        return Utils.convertToQuery(query)
    }

    fun fetchArtists(query: String?) {
        _artistResult.value = repository.fetchArtist(convertToQueryForDb(query))
    }

    override fun onCleared() {
        super.onCleared()
        repository.disposeJob()
    }

    fun setTrackSelected(track:Track?){
        _selectedTrackLiveData.value=track

    }
    fun setArtistSelected(artist:Artist2?){
        _selectedArtistLiveData.value=artist
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
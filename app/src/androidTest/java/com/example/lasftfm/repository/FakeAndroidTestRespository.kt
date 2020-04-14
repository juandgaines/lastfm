package com.example.lasftfm.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import com.example.lasftfm.UtilsTesting
import com.example.lasftfm.repository.db.ListDataSource

class FakeAndroidTestRespository : RepoOperations {

    private var _tracks = UtilsTesting.getTrackList()
    private var _artist = UtilsTesting.getArtisList()
    private var networkErrors = MutableLiveData<String>()

    override fun fetch(query: String): TrackResults {

        val dataSourceFactory = ListDataSource(_tracks)
        val data = LivePagedListBuilder(dataSourceFactory!!, 20)
            .build()

        return TrackResults(data, networkErrors)

    }

    override fun fetchArtist(query: String): ArtistsResults {
        val dataSourceFactory = ListDataSource(_artist)
        val data = LivePagedListBuilder(dataSourceFactory!!, 20)
            .build()

        return ArtistsResults(data, networkErrors)
    }

    override fun disposeJob() {
        TODO("Not yet implemented")
    }
}
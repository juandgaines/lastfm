package com.example.lasftfm.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.lasftfm.repository.network.Track

data class TrackResults(
    val data: LiveData<PagedList<Track>>,
    val networkErrors: LiveData<String>
)


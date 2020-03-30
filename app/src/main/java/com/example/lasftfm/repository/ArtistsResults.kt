package com.example.lasftfm.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.lasftfm.network.Artist2
import com.example.lasftfm.network.Track

data class ArtistsResults(
    val data: LiveData<PagedList<Artist2>>,
    val networkErrors: LiveData<String>
)
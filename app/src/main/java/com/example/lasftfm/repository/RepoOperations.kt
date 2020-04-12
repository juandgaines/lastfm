package com.example.lasftfm.repository

interface RepoOperations {
    fun fetch(query: String): TrackResults
    fun fetchArtist(query: String): ArtistsResults
    fun disposeJob()
}
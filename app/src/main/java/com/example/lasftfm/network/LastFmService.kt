package com.example.lasftfm.network

import com.example.lasftfm.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmService {
    @GET("2.0/?method=geo.gettoptracks&format=json")
    suspend fun getTracksList(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.LASTFM_KEY
    ): ResponseTrack

    @GET("2.0/?method=geo.gettopartists&format=json")
    suspend fun getArtistList(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.LASTFM_KEY
    ): ResponseArtist

}
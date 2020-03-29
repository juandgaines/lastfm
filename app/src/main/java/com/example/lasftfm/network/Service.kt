package com.example.lasftfm.network

import com.example.lasftfm.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface LastFmService {
    @GET("2.0/?method=geo.gettoptracks&format=json")
    suspend fun getTracksList(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String=BuildConfig.LASTFM_KEY
    ): ResponseTrack

    @GET("2.0/?method=geo.gettopartists&format=json")
    suspend fun getArtistList(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String=BuildConfig.LASTFM_KEY
    ): ResponseTrack

}

object Network {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ws.audioscrobbler.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val lastFm = retrofit.create(LastFmService::class.java)
}
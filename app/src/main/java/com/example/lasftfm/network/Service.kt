package com.example.lasftfm.network

import android.util.Log
import com.example.lasftfm.BuildConfig
import com.example.lasftfm.repository.LastFmRepo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber
import java.util.logging.Level

suspend fun searchTracks(
    service: LastFmService,
    page: Int,
    onSuccess: (tracks: List<Track>) -> Unit,
    onError: (error: String) -> Unit
) {
    try {
        val playList = service.getTracksList("spain", page).tracks
        onSuccess(playList.track)
    } catch (e: Throwable) {
        onError(e.message.toString())
        Timber.tag(LastFmRepo::class.java.simpleName).e( "$e")
    }
}

suspend fun searchArtists(
    service: LastFmService,
    page: Int,
    onSuccess: (artists: List<Artist2>) -> Unit,
    onError: (error: String) -> Unit
) {
    try {
        val playList = service.getArtistList("spain", page).topartists
        onSuccess(playList.artist)
    } catch (e: Throwable) {
        onError(e.message.toString())
        Timber.tag(LastFmRepo::class.java.simpleName).e( "$e")
    }
}

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

object Network {
    private val logger = HttpLoggingInterceptor()
    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    init {
        logger.level = HttpLoggingInterceptor.Level.BASIC
    }


    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ws.audioscrobbler.com/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val lastFm = retrofit.create(LastFmService::class.java)
}
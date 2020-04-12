package com.example.lasftfm.network

import com.example.lasftfm.repository.LastFmRepo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber


class Network {

    private var logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
    private var client: OkHttpClient
    private var moshi: Moshi
    private var retrofit: Retrofit


    init {
        logger.level = HttpLoggingInterceptor.Level.BASIC
        client = OkHttpClient.Builder().addInterceptor(logger).build()
        moshi = Moshi.Builder().add(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()


    }

    val lastFm = retrofit.create(LastFmService::class.java)


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
            Timber.tag(LastFmRepo::class.java.simpleName).e("$e")
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
            Timber.tag(LastFmRepo::class.java.simpleName).e("$e")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Network? = null

        fun getNetworkProvider(): Network {

            return INSTANCE ?: synchronized(this) {

                Network()
            }
        }

    }
}


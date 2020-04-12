package com.example.lasftfm.repository.network

import androidx.paging.DataSource
import com.example.lasftfm.repository.LastFmDataSource
import com.example.lasftfm.repository.LastFmRepo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber


class Network : LastFmDataSource {

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
        page: Int,
        onSuccess: (tracks: List<Track>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        try {
            val playList = lastFm.getTracksList("spain", page).tracks
            onSuccess(playList.track)
        } catch (e: Throwable) {
            onError(e.message.toString())
            Timber.tag(LastFmRepo::class.java.simpleName).e("$e")
        }
    }

    suspend fun searchArtists(
        page: Int,
        onSuccess: (artists: List<Artist2>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        try {
            val playList = lastFm.getArtistList("spain", page).topartists
            onSuccess(playList.artist)
        } catch (e: Throwable) {
            onError(e.message.toString())
            Timber.tag(LastFmRepo::class.java.simpleName).e("$e")
        }
    }

    override fun insertTracks(posts: List<Track>) = Unit

    override fun getAllTracks(query: String): DataSource.Factory<Int, Track>? = null

    override fun insertArtist(posts: List<Artist2>) = Unit

    override fun getAllArtist(query: String): DataSource.Factory<Int, Artist2>? = null

    override fun deleteAllArtists() = Unit

    override fun deleteAllTracks() = Unit
    override suspend fun searchArtistsByQuery(
        page: Int,
        onSuccess: (artists: List<Artist2>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        searchArtists(page, onSuccess, onError)
    }

    override suspend fun searchTrackByQuery(
        page: Int,
        onSuccess: (artists: List<Track>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        searchTracks(page, onSuccess, onError)
    }

    override fun setCoroutine(coroutineScope: CoroutineScope) = Unit

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


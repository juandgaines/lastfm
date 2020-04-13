package com.example.lasftfm.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lasftfm.getOrAwaitValue
import com.example.lasftfm.repository.db.LastFmFakeDataSource
import com.example.lasftfm.repository.network.Artist
import com.example.lasftfm.repository.network.Artist2
import com.example.lasftfm.repository.network.Image
import com.example.lasftfm.repository.network.Track
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LastFmRepoTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val track1 = Track(
        "",
        "God save the queen",
        "",
        "",
        "",
        listOf<Image>(),
        Artist("", "Sex pistols", "")
    )
    private val track2 = Track(
        "",
        "Rojo",
        "",
        "",
        "",
        listOf<Image>(),
        Artist("", "J Balvin", "")
    )

    private val track3 = Track(
        "",
        "Azul",
        "",
        "",
        "",
        listOf<Image>(),
        Artist("", "J Balvin", "")
    )

    private val artist1 = Artist2(
        name = "Radiohead",
        listeners = "4847833",
        mbid = "a74b1b7f-71a5-4011-9441-d0b5e4122711",
        url = "https://www.last.fm/music/Radiohead",
        streamable = "0",
        image = listOf(
            Image(
                "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                "small"
            )
        )
    )

    private val artist2 = Artist2(
        name = "J Balvin",
        listeners = "4847833",
        mbid = "a74b1b7f-71a5-4011-9441-d0b5e412245",
        url = "https://www.last.fm/music/Jbalvin",
        streamable = "0",
        image = listOf(
            Image(
                "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                "small"
            )
        )
    )

    private val artist3 = Artist2(
        name = "Calvin Harris",
        listeners = "4847833",
        mbid = "a74b1b7f-71a5-4011-9441-d0b5e412245",
        url = "https://www.last.fm/music/Calvin",
        streamable = "0",
        image = listOf(
            Image(
                "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                "small"
            )
        )
    )

    private val artistList = listOf(artist1, artist2, artist3)
    private val trackList = listOf(track1, track2, track3)
    private lateinit var localDataSource: LastFmDataSource
    private lateinit var remoteDataSource: LastFmDataSource
    private lateinit var lastFmRepo: LastFmRepo

    @Before
    fun createRepository() {
        localDataSource =
            LastFmFakeDataSource(
                artistList.toMutableList(),
                trackList.toMutableList()
            )

        remoteDataSource =
            LastFmFakeDataSource(
                artistList.toMutableList(),
                trackList.toMutableList()
            )
        lastFmRepo = LastFmRepo(
            remoteDataSource,
            localDataSource
        )

    }


    @Test
    fun fetch_requestPagedTracks() {

        val result = lastFmRepo.fetch("")
        val tracks = result.data.getOrAwaitValue()
        assertThat(tracks, not(nullValue()))
    }

    @Test
    fun fetchArtist_requestPagedArtist() {

        val result = lastFmRepo.fetchArtist("")
        val tracks = result.data.getOrAwaitValue()
        assertThat(tracks, not(nullValue()))
    }

    @After
    fun disposeJob(){
        lastFmRepo.disposeJob()
    }

}
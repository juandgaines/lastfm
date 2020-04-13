package com.example.lasftfm.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lasftfm.UtilsTesting
import com.example.lasftfm.getOrAwaitValue
import com.example.lasftfm.repository.db.LastFmFakeDataSource
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
    private lateinit var localDataSource: LastFmDataSource
    private lateinit var remoteDataSource: LastFmDataSource
    private lateinit var lastFmRepo: LastFmRepo

    @Before
    fun createRepository() {
        localDataSource =
            LastFmFakeDataSource(
                UtilsTesting.getArtisList(),
                UtilsTesting.getTrackList()
            )

        remoteDataSource =
            LastFmFakeDataSource(
                UtilsTesting.getArtisList(),
                UtilsTesting.getTrackList()
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
    fun disposeJob() {
        lastFmRepo.disposeJob()
    }

}
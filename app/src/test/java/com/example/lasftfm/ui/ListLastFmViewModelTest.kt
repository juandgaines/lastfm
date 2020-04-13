package com.example.lasftfm.ui


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lasftfm.getOrAwaitValue
import com.example.lasftfm.repository.FakeTestRepository
import com.example.lasftfm.repository.network.Artist
import com.example.lasftfm.repository.network.Artist2
import com.example.lasftfm.repository.network.Image
import com.example.lasftfm.repository.network.Track
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ListLastFmViewModelTest {

    private lateinit var repository: FakeTestRepository
    private lateinit var lastFmViewModel: ListLastFmViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createRepository() {
        repository = FakeTestRepository()
        lastFmViewModel = ListLastFmViewModel(repository)
    }

    @Test
    fun selectedTrack_setTrackSelected() {

        lastFmViewModel.setTrackSelected(
            Track(
                "",
                "God save the queen",
                "",
                "",
                "",
                listOf<Image>(),
                Artist("", "", "")
            )
        )
        val value = lastFmViewModel.selectedTrackLiveData.getOrAwaitValue()
        assertEquals(value.name, "God save the queen")
    }

    @Test
    fun selectedArtist_setArtistSelected() {

        lastFmViewModel.setArtistSelected(
            Artist2(
                "",
                listOf<Image>(),
                "",
                "Bad Bunny",
                "",
                ""
            )
        )
        val value = lastFmViewModel.selectedArtistLiveData.getOrAwaitValue()
        assertEquals(value.name, "Bad Bunny")
    }


    @Test
    fun fetchTracks_dummyData_returnFakeDataOkAndErrorNull() {

        lastFmViewModel.fetchTracks("")
        val tracksResult = lastFmViewModel.tracks.getOrAwaitValue()
        val trackErrors = lastFmViewModel.networkErrorsArtist.value
        Assert.assertThat(tracksResult, CoreMatchers.not(CoreMatchers.nullValue()))
        Assert.assertThat(trackErrors, CoreMatchers.nullValue())
    }

    @Test
    fun fetchArtist_dummyData_returnFakeDataOkAndErrorNull() {

        lastFmViewModel.fetchArtists("")
        val artistResult = lastFmViewModel.artists.getOrAwaitValue()
        val artistErrors = lastFmViewModel.networkErrors.value
        Assert.assertThat(artistResult, CoreMatchers.not(CoreMatchers.nullValue()))
        Assert.assertThat(artistErrors, CoreMatchers.nullValue())

    }

}
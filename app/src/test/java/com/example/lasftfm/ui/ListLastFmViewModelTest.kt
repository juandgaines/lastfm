package com.example.lasftfm.ui


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lasftfm.getOrAwaitValue
import com.example.lasftfm.repository.network.Artist
import com.example.lasftfm.repository.network.Artist2
import com.example.lasftfm.repository.network.Image
import com.example.lasftfm.repository.network.Track
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListLastFmViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun selectedTrack_setTrackSelected() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val lastFmViewModel = ListLastFmViewModel(context)

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
        val value=lastFmViewModel.selectedTrackLiveData.getOrAwaitValue()
        assertEquals(value.name,"God save the queen")
    }

    @Test
    fun selectedArtist_setArtistSelected() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val lastFmViewModel = ListLastFmViewModel(context)

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
        val value=lastFmViewModel.selectedArtistLiveData.getOrAwaitValue()
        assertEquals(value.name,"Bad Bunny")
    }

}
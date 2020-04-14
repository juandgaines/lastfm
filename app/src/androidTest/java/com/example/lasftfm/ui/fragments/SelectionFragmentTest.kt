package com.example.lasftfm.ui.fragments

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.lasftfm.R
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@MediumTest
class SelectionFragmentTest {


    @Test
    fun testFragment() {

        launchFragmentInContainer<SelectionFragment>(null, R.style.AppTheme)
        Thread.sleep(2000)
    }
}
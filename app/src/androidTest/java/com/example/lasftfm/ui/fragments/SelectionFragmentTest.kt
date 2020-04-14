package com.example.lasftfm.ui.fragments

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.lasftfm.R
import com.example.lasftfm.ServiceLocator
import com.example.lasftfm.repository.FakeAndroidTestRespository
import com.example.lasftfm.repository.RepoOperations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class SelectionFragmentTest {

    private lateinit var repository: RepoOperations

    @Before
    fun initRepository(){

        repository=FakeAndroidTestRespository()
        ServiceLocator.lastFmRepo=repository

    }

    @Test
    fun testFragment() {

        launchFragmentInContainer<SelectionFragment>(null, R.style.AppTheme)
        Thread.sleep(2000)
    }


    @InternalCoroutinesApi
    @After
    fun cleanUpDb()= runBlocking {
        ServiceLocator.resetRepository()
    }
}
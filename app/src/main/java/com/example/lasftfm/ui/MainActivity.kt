package com.example.lasftfm.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.lasftfm.R
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private lateinit var lastFmViewModel: ListLastFmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val application = application

        val dataSource = LastFmDatabase.getInstance(application).lastFmDao()

        val viewModelFactory = LastFmViewModelFactory(dataSource, application)

        lastFmViewModel =
            ViewModelProvider(this, viewModelFactory).get(ListLastFmViewModel::class.java)


        lastFmViewModel.getTracks().observe(this, Observer {
            it
        })

        lastFmViewModel.fetchTracks()
    }
}

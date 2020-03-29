package com.example.lasftfm.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.lasftfm.R
import com.example.lasftfm.databinding.ActivityMainBinding
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private lateinit var lastFmViewModel: ListLastFmViewModel
    private lateinit var databinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val application = application
        databinding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        val dataSource = LastFmDatabase.getInstance(application).lastFmDao()

        val viewModelFactory = LastFmViewModelFactory(application)

        lastFmViewModel =
            ViewModelProvider(this, viewModelFactory).get(ListLastFmViewModel::class.java)

        lastFmViewModel.tracks.observe(this, Observer {
            databinding.trackList.apply {
                adapter=TrackAdapter(it)
            }
        })

    }
}

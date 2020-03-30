package com.example.lasftfm.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.lasftfm.R
import com.example.lasftfm.databinding.ActivityMainBinding
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.Network
import com.example.lasftfm.repository.LastFmRepo
import com.example.lasftfm.ui.adapters.ArtistListener
import com.example.lasftfm.ui.adapters.ArtistsAdapter
import com.example.lasftfm.ui.adapters.TrackAdapter
import com.example.lasftfm.ui.adapters.TrackListener


class MainActivity : AppCompatActivity() {


    private lateinit var lastFmViewModel: ListLastFmViewModel
    private lateinit var databinding:ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var adapterTracks: TrackAdapter
    private lateinit var adapterArtists: ArtistsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = application
        databinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        navController = findNavController(R.id.myNavHostFragment)
        val dataSource = LastFmDatabase.getInstance(application)
        val repo= LastFmRepo(Network.lastFm,dataSource)
        val viewModelFactory = LastFmViewModelFactory(repo)
        lastFmViewModel =
            ViewModelProvider(this, viewModelFactory).get(ListLastFmViewModel::class.java)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        databinding.toolbar.setupWithNavController(navController,appBarConfiguration)
    }
}

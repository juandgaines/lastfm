package com.example.lasftfm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.lasftfm.MyApplication
import com.example.lasftfm.R
import com.example.lasftfm.databinding.ActivityMainBinding
import com.example.lasftfm.ui.adapters.ArtistsAdapter
import com.example.lasftfm.ui.adapters.TrackAdapter


class MainActivity : AppCompatActivity() {


    private lateinit var lastFmViewModel: ListLastFmViewModel
    private lateinit var databinding: ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var adapterTracks: TrackAdapter
    private lateinit var adapterArtists: ArtistsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = application
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.myNavHostFragment)

        val viewModelFactory =
            LastFmViewModelFactory((applicationContext as MyApplication).repoLastFm)
        lastFmViewModel =
            ViewModelProvider(this, viewModelFactory).get(ListLastFmViewModel::class.java)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        databinding.toolbar.setupWithNavController(navController, appBarConfiguration)

    }
}

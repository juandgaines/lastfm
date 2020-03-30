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
        initAdapter()

/*        databinding.trackList.apply {
            adapter=adapterTracks
        }
        databinding.artistList.apply {
            adapter=adapterArtists
        }
        lastFmViewModel.tracks.observe(this, Observer {
            adapterTracks.submitList(it)
        })
        lastFmViewModel.artists.observe(this, Observer {
            adapterArtists.submitList(it)
        })*/

    }


    private fun initAdapter() {
/*        //tracks
        adapterTracks=
            TrackAdapter(TrackListener {
                Toast.makeText(this@MainActivity, "$it", Toast.LENGTH_SHORT).show()
            })
        databinding.trackList.adapter = adapterTracks
        lastFmViewModel.tracks.observe(this, Observer{
            Log.d("Activity", "list: ${it?.size}")
            adapterTracks.submitList(it)
        })
        lastFmViewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
        })

        //artists

        adapterArtists=
            ArtistsAdapter(ArtistListener {
                Toast.makeText(this@MainActivity, "$it", Toast.LENGTH_SHORT).show()
            })
        databinding.artistList.adapter = adapterTracks
        lastFmViewModel.artists.observe(this, Observer{
            Log.d("Activity", "list: ${it?.size}")
            adapterArtists.submitList(it)
        })
        lastFmViewModel.networkErrorsArtist.observe(this, Observer<String> {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
        })*/
    }
}

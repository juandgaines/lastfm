package com.example.lasftfm.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.example.lasftfm.R
import com.example.lasftfm.databinding.ActivityMainBinding
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.Network
import com.example.lasftfm.network.Track
import com.example.lasftfm.repository.LastFmRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private lateinit var lastFmViewModel: ListLastFmViewModel
    private lateinit var databinding:ActivityMainBinding
    private lateinit var adapterTracks: TrackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val application = application
        databinding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        val dataSource = LastFmDatabase.getInstance(application)

        val repo= LastFmRepo(Network.lastFm,dataSource)
        val viewModelFactory = LastFmViewModelFactory(repo)
        lastFmViewModel =
            ViewModelProvider(this, viewModelFactory).get(ListLastFmViewModel::class.java)
        initAdapter()

        databinding.trackList.apply {
            adapter=adapterTracks
        }
        lastFmViewModel.tracks.observe(this, Observer {
            adapterTracks.submitList(it)
        })

    }


    private fun initAdapter() {
        adapterTracks= TrackAdapter(TrackListener {
            Toast.makeText(this@MainActivity, "$it",Toast.LENGTH_SHORT).show()
        })
        databinding.trackList.adapter = adapterTracks
        lastFmViewModel.tracks.observe(this, Observer{
            Log.d("Activity", "list: ${it?.size}")
            adapterTracks.submitList(it)
        })
        lastFmViewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
        })
    }
}

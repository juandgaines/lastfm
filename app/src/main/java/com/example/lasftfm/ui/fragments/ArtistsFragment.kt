package com.example.lasftfm.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.lasftfm.R
import com.example.lasftfm.databinding.FragmentArtistsBinding
import com.example.lasftfm.databinding.FragmentTracksBinding
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.Network
import com.example.lasftfm.repository.LastFmRepo
import com.example.lasftfm.ui.LastFmViewModelFactory
import com.example.lasftfm.ui.ListLastFmViewModel
import com.example.lasftfm.ui.adapters.ArtistListener
import com.example.lasftfm.ui.adapters.ArtistsAdapter
import com.example.lasftfm.ui.adapters.TrackAdapter
import com.example.lasftfm.ui.adapters.TrackListener

/**
 * A simple [Fragment] subclass.
 */
class ArtistsFragment : Fragment() {

    private lateinit var lastFmViewModel: ListLastFmViewModel
    private lateinit var adapterArtists: ArtistsAdapter
    private lateinit var binding: FragmentArtistsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val dataSource = LastFmDatabase.getInstance(activity!!.application)
        val repo = LastFmRepo(Network.lastFm, dataSource)
        val viewModelFactory = LastFmViewModelFactory(repo)
        lastFmViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(ListLastFmViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_artists, container, false)

        initAdapter()

        binding.artistList.apply {
            adapter = adapter
        }
        return binding.root
    }


    private fun initAdapter() {
        //tracks
        adapterArtists =
            ArtistsAdapter(ArtistListener {
                Toast.makeText(activity!!.applicationContext, "$it", Toast.LENGTH_SHORT).show()
            })
        binding.artistList.adapter = adapterArtists
        lastFmViewModel.artists.observe(viewLifecycleOwner, Observer {
            Log.d("Activity", "list: ${it?.size}")
            adapterArtists.submitList(it)
        })
        lastFmViewModel.networkErrors.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(activity!!.applicationContext, "Error: $it", Toast.LENGTH_LONG).show()
        })
    }

}
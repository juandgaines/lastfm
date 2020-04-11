package com.example.lasftfm.ui.fragments

import android.app.DownloadManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.lasftfm.R
import com.example.lasftfm.databinding.FragmentTracksBinding
import com.example.lasftfm.db.LastFmDatabase
import com.example.lasftfm.network.Network
import com.example.lasftfm.repository.LastFmRepo
import com.example.lasftfm.ui.LastFmViewModelFactory
import com.example.lasftfm.ui.ListLastFmViewModel
import com.example.lasftfm.ui.adapters.TrackAdapter
import com.example.lasftfm.ui.adapters.TrackListener
import timber.log.Timber


class TracksFragment : Fragment() {

    private lateinit var lastFmViewModel: ListLastFmViewModel
    private lateinit var adapterTracks: TrackAdapter
    private lateinit var binding: FragmentTracksBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewModelFactory = LastFmViewModelFactory(activity!!.application)
        lastFmViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(ListLastFmViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tracks, container, false)
        initAdapter()

        binding.trackList.apply {
            adapter = adapterTracks
        }

        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query= s.toString()
                lastFmViewModel.queryLiveDataTracks=query
                adapterTracks.submitList(null)
                lastFmViewModel.fetchTracks(query)
            }

        })
        binding.searchText.setText(lastFmViewModel.queryLiveDataTracks)

        lastFmViewModel.tracks.observe(viewLifecycleOwner, Observer {
            Timber.tag(TracksFragment::class.java.simpleName).d( "list: ${it?.size}")
            adapterTracks.submitList(it)
        })
        lastFmViewModel.networkErrors.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(activity!!.applicationContext, "Error: $it", Toast.LENGTH_LONG).show()
        })
        lastFmViewModel.setTrackSelected(null)

        return binding.root
    }


    private fun initAdapter() {
        //tracks
        adapterTracks =
            TrackAdapter(TrackListener {
                lastFmViewModel.setTrackSelected(it)
                activity?.findNavController(R.id.myNavHostFragment)?.navigate(R.id.action_tracksFragment_to_detailFragment)
            })
        binding.trackList.adapter = adapterTracks

    }

}

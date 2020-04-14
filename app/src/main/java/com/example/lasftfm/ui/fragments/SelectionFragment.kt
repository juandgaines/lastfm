package com.example.lasftfm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lasftfm.R
import com.example.lasftfm.databinding.FragmentSelectionBinding

class SelectionFragment : Fragment() {

    private lateinit var binding: FragmentSelectionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selection, container, false)

        binding.tracks.setOnClickListener {
            activity?.findNavController(R.id.myNavHostFragment)
                ?.navigate(R.id.action_selectionFragment_to_tracksFragment)
        }
        binding.artist.setOnClickListener {
            activity?.findNavController(R.id.myNavHostFragment)
                ?.navigate(R.id.action_selectionFragment_to_artistsFragment)
        }
        return binding.root
    }

}

package com.example.lasftfm.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lasftfm.R
import com.example.lasftfm.databinding.FragmentDetailBinding
import com.example.lasftfm.repository.LastFmRepo
import com.example.lasftfm.ui.LastFmViewModelFactory
import com.example.lasftfm.ui.ListLastFmViewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var lastFmViewModel: ListLastFmViewModel
    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory = LastFmViewModelFactory(LastFmRepo.getRepository(requireActivity().application))
        lastFmViewModel =
            ViewModelProvider(activity!!, viewModelFactory).get(ListLastFmViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container, false)

        lastFmViewModel.selectedTrackLiveData.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.urlValue.text=it.url
                val dataImage=it.image.lastOrNull()
                dataImage.let {
                    Glide.with(this).load(dataImage?.text).into(binding.imageView)
                }
                binding.listenersValue.text=it.listeners
                binding.nameValue.text=it.name
                binding.goToUrlLabel.setOnClickListener {view->
                    val webpage: Uri = Uri.parse(it.url)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    if (intent.resolveActivity(activity?.packageManager!!) != null) {
                        startActivity(intent)
                    }
                }
            }
        })

        lastFmViewModel.selectedArtistLiveData.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.urlValue.text=it.url
                val dataImage=it.image.lastOrNull()
                dataImage.let {
                    Glide.with(this).load(dataImage?.text).into(binding.imageView)
                }
                binding.listenersValue.text=it.listeners
                binding.nameValue.text=it.name
                binding.goToUrlLabel.setOnClickListener {view->
                    val webpage: Uri = Uri.parse(it.url)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    if (intent.resolveActivity(activity?.packageManager!!) != null) {
                        startActivity(intent)
                    }
                }
            }
        })
        return binding.root
    }

}

package com.example.lasftfm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lasftfm.R
import com.example.lasftfm.databinding.ItemTrackViewBinding
import com.example.lasftfm.network.Track
import kotlinx.coroutines.flow.combine

class TrackAdapter: ListAdapter<Track, TrackViewHolder>(TracksDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class TrackViewHolder private  constructor(val binding: ItemTrackViewBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Track) {
        val context=binding.containerTrackItem.context
        binding.track=item
        Glide.with(context).load(item.image.last().text).centerCrop().into(binding.trackPick)
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TrackViewHolder {
            val layoutInflater =  LayoutInflater.from(parent.context)
            val binding = ItemTrackViewBinding.inflate(layoutInflater, parent, false)
            return TrackViewHolder(binding)
        }
    }

}

class TracksDiffCallback:DiffUtil.ItemCallback<Track>(){
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.mbid==newItem.mbid
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.mbid==newItem.mbid
    }

}

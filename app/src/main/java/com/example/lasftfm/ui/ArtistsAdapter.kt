package com.example.lasftfm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lasftfm.databinding.ItemArtistViewBinding
import com.example.lasftfm.databinding.ItemTrackViewBinding
import com.example.lasftfm.network.Artist2
import com.example.lasftfm.network.Track

class ArtistsAdapter(val trackListener: ArtistListener) :
    PagedListAdapter<Artist2, ArtistsViewHolder>(ArtistsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsViewHolder {
        return ArtistsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArtistsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, trackListener)
    }
}

class ArtistsViewHolder private constructor(val binding: ItemArtistViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Artist2?,
        trackListener: ArtistListener
    ) {
        val context = binding.containerTrackItem.context
        binding.artist = item
        binding.listener=trackListener
        item?.let {
            Glide.with(context).load(it.image.last().text).centerCrop().into(binding.trackPick)
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ArtistsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemArtistViewBinding.inflate(layoutInflater, parent, false)
            return ArtistsViewHolder(binding)
        }
    }

}

class ArtistsDiffCallback : DiffUtil.ItemCallback<Artist2>() {
    override fun areItemsTheSame(oldItem: Artist2, newItem: Artist2): Boolean {
        return oldItem.mbid == newItem.mbid
    }

    override fun areContentsTheSame(oldItem: Artist2, newItem: Artist2): Boolean {
        return oldItem.mbid == newItem.mbid
    }

}

class ArtistListener(val clickListener: (trackId: String) -> Unit) {
    fun onClick(artist: Artist2) = clickListener(artist.mbid)
}
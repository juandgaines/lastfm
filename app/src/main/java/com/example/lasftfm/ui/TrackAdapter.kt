package com.example.lasftfm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lasftfm.R
import com.example.lasftfm.network.Track

class TrackAdapter: ListAdapter<Track, TrackViewHolder>(TracksDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = getItem(position)
        holder.nameText.text = item.name
    }
}

class TrackViewHolder private  constructor(itemView: View): RecyclerView.ViewHolder(itemView){
    val nameText: TextView = itemView.findViewById(R.id.track_name)

    fun bind(item: Track) {
        val name = itemView.context.resources
    }

    companion object {
        fun from(parent: ViewGroup): TrackViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.item_track_view, parent, false)
            return TrackViewHolder(view)
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

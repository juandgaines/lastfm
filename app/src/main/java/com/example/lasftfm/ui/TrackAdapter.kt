package com.example.lasftfm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lasftfm.R
import com.example.lasftfm.network.Track

class TrackAdapter(val data:List<Track>): RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.from(parent)
    }

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = data[position]
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
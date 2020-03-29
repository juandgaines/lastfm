package com.example.lasftfm.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

//region tracks
data class ResponseTrack(
    val tracks: Tracks
)

data class Tracks(
    @Json(name = "@attr")
    val attr: Attr,
    val track: List<Track>
)

data class Attr(
    val country: String,
    val page: String,
    val perPage: String,
    val total: String,
    val totalPages: String
)

@Entity(tableName = "tracks")
data class Track(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val mbid: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "duration")
    val duration: String,
    @ColumnInfo(name = "listeners")
    val listeners: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "images")
    val image: List<Image>,
    @ColumnInfo(name = "artist")
    val artist: Artist
)

data class Artist(
    val mbid: String,
    val name: String,
    val url: String
)

data class Image(
    @Json(name = "#text")
    val text: String,
    val size: String
)
//endregion
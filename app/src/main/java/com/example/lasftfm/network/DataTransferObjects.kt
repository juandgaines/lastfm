package com.example.lasftfm.network

import com.squareup.moshi.Json
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

data class Track(
    @Json(name = "@attr")
    val attr: AttrX,
    val artist: Artist,
    val duration: String,
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val streamable: Streamable,
    val url: String
)

data class AttrX(
    val rank: String
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

data class Streamable(
    @Json(name = "#text")
    val text: String,
    val fulltrack: String
)
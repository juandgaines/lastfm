package com.example.lasftfm.repository.db

import androidx.room.TypeConverter
import com.example.lasftfm.repository.network.Artist
import com.example.lasftfm.repository.network.Artist2
import com.example.lasftfm.repository.network.Image
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class DataConverter {

    @TypeConverter
    fun stringToTracksImagesList(images: String): List<Image> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, Image::class.java)
        val adapter = moshi.adapter<List<Image>>(type)
        return adapter.fromJson(images)!!
    }

    @TypeConverter
    fun imagesListToString(images: List<Image>?): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, Image::class.java)
        val jsonAdapter = moshi.adapter<List<Image>>(type)
        val json = jsonAdapter.toJson(images)
        return json

    }

    @TypeConverter
    fun stringToArtis(artist: String): Artist {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Artist::class.java)
        return adapter.fromJson(artist)!!
    }

    @TypeConverter
    fun artistToString(artist: Artist): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter<Artist>(Artist::class.java)
        val json = jsonAdapter.toJson(artist)
        return json

    }


    @TypeConverter
    fun stringToArtist2(artist: String): Artist2 {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Artist2::class.java)
        return adapter.fromJson(artist)!!
    }

    @TypeConverter
    fun artistToString(artist: Artist2): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter<Artist2>(Artist2::class.java)
        val json = jsonAdapter.toJson(artist)
        return json

    }


}
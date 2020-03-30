package com.example.lasftfm.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.lasftfm.network.Artist2
import com.example.lasftfm.network.Track

@Dao
interface LastFmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Track>)

    @Query("SELECT * FROM tracks  WHERE (name LIKE :query) ORDER BY name ASC")
    fun listOfTracks(query:String):DataSource.Factory<Int, Track>

    @Query("DELETE FROM tracks")
    fun clearTracks()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtist(posts: List<Artist2>)

    @Query("SELECT * FROM artists WHERE (name LIKE :query) ORDER BY name ASC")
    fun listOfArtist(query:String):DataSource.Factory<Int, Artist2>

    @Query("DELETE FROM artists")
    fun clearArtist()


}
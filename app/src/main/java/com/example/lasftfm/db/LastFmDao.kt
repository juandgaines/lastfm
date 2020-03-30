package com.example.lasftfm.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.lasftfm.network.Track

@Dao
interface LastFmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Track>)

    @Query("SELECT * FROM tracks ORDER BY id ASC")
    fun listOfTracks():DataSource.Factory<Int, Track>

    @Query("DELETE FROM tracks")
    fun clearTracks()


}
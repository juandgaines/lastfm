package com.example.lasftfm.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lasftfm.network.Track

interface LastFmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Track>)

    @Query("SELECT * FROM tracks ORDER BY id ASC")
    fun reposByName(): LiveData<List<Track>>

    @Query("DELETE FROM tracks")
    fun clearTracks()


}
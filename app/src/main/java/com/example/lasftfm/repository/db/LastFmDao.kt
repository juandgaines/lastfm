package com.example.lasftfm.repository.db

import androidx.paging.DataSource
import androidx.room.*
import com.example.lasftfm.repository.network.Artist2
import com.example.lasftfm.repository.network.Track

@Dao
interface LastFmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<Track>)

    @Query("SELECT * FROM tracks  WHERE (name LIKE :query) ORDER BY name ASC")
    fun listOfTracks(query:String):DataSource.Factory<Int, Track>

    @Query("DELETE FROM tracks")
    suspend fun clearTracks()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(posts: List<Artist2>)

    @Query("SELECT * FROM artists WHERE (name LIKE :query) ORDER BY name ASC")
    fun listOfArtist(query:String):DataSource.Factory<Int, Artist2>

    @Query("DELETE FROM artists")
    suspend fun clearArtist()


}
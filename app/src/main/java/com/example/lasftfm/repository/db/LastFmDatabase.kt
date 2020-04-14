package com.example.lasftfm.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lasftfm.repository.network.Artist2
import com.example.lasftfm.repository.network.Track

@Database(
    entities = [Track::class, Artist2::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class LastFmDatabase : RoomDatabase() {

    abstract fun lastFmDao(): LastFmDao

}
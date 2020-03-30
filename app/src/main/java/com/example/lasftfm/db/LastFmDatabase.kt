package com.example.lasftfm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lasftfm.network.Artist2
import com.example.lasftfm.network.Track

@Database(
    entities = [Track::class, Artist2::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class LastFmDatabase : RoomDatabase() {

    abstract fun lastFmDao(): LastFmDao

    companion object {

        @Volatile
        private var INSTANCE: LastFmDatabase? = null

        fun getInstance(context: Context): LastFmDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                LastFmDatabase::class.java, "lastfm_database.db")
                .build()
    }
}
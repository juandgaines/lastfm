package com.example.lasftfm

import android.content.Context
import androidx.room.Room
import com.example.lasftfm.repository.LastFmDataSource
import com.example.lasftfm.repository.LastFmRepo
import com.example.lasftfm.repository.db.LastFmCache
import com.example.lasftfm.repository.db.LastFmDatabase
import com.example.lasftfm.repository.network.Network

object ServiceLocator {

    private var database:LastFmDatabase?=null
    @Volatile
    var lastFmRepo:LastFmRepo?=null

    fun provideLastFmRepository(context: Context): LastFmRepo {
        synchronized(this) {
            return lastFmRepo ?: createLastFmRepository(context)
        }
    }

    private fun createLastFmRepository(context: Context): LastFmRepo {
        val newRepo = LastFmRepo( Network.getNetworkProvider(), createLastFmLocalDataSource(context))
        lastFmRepo = newRepo
        return newRepo
    }

    private fun createLastFmLocalDataSource(context: Context): LastFmDataSource {
        val database = database ?: createDataBase(context)
        return LastFmCache(database)
    }

    private fun createDataBase(context: Context): LastFmDatabase {
        val result =  Room.databaseBuilder(context.applicationContext,
            LastFmDatabase::class.java, "lastfm_database.db")
            .build()
        database = result
        return result
    }

}
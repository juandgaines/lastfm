package com.example.lasftfm

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.lasftfm.repository.LastFmDataSource
import com.example.lasftfm.repository.LastFmRepo
import com.example.lasftfm.repository.RepoOperations
import com.example.lasftfm.repository.db.LastFmCache
import com.example.lasftfm.repository.db.LastFmDatabase
import com.example.lasftfm.repository.network.Network
import kotlinx.coroutines.InternalCoroutinesApi

import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private val lock = Any()
    private var database: LastFmDatabase? = null

    @Volatile
    var lastFmRepo: RepoOperations? = null
        @VisibleForTesting set


    fun provideLastFmRepository(context: Context): RepoOperations {
        synchronized(this) {
            return lastFmRepo ?: createLastFmRepository(context)
        }
    }

    private fun createLastFmRepository(context: Context): RepoOperations {
        val newRepo = LastFmRepo(Network.getNetworkProvider(), createLastFmLocalDataSource(context))
        lastFmRepo = newRepo
        return newRepo
    }

    private fun createLastFmLocalDataSource(context: Context): LastFmDataSource {
        val database = database ?: createDataBase(context)
        return LastFmCache(database)
    }

    private fun createDataBase(context: Context): LastFmDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            LastFmDatabase::class.java, "lastfm_database.db"
        )
            .build()
        database = result
        return result
    }

    @InternalCoroutinesApi
    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
                database?.apply {
                    clearAllTables()
                    close()
                }
                database = null
                lastFmRepo = null
        }
    }
}
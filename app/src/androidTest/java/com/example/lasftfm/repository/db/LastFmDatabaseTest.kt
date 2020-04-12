package com.example.lasftfm.repository.db

import androidx.room.Room
import androidx.room.paging.LimitOffsetDataSource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.lasftfm.repository.network.Artist
import com.example.lasftfm.repository.network.Image
import com.example.lasftfm.repository.network.Track
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LastFmDatabaseTest {

    private lateinit var lastFmDao: LastFmDao
    private lateinit var db: LastFmDatabase
    private lateinit var track:Track
    private  var trackJson="{\n" +
            "        \"name\": \"Everlong\",\n" +
            "        \"duration\": \"323\",\n" +
            "        \"listeners\": \"1368885\",\n" +
            "        \"mbid\": \"009f7e7b-5ecf-4cef-a2c1-0f0c736e807e\",\n" +
            "        \"url\": \"https://www.last.fm/music/Foo+Fighters/_/Everlong\",\n" +
            "        \"streamable\": {\n" +
            "          \"#text\": \"0\",\n" +
            "          \"fulltrack\": \"0\"\n" +
            "        },\n" +
            "        \"artist\": {\n" +
            "          \"name\": \"Foo Fighters\",\n" +
            "          \"mbid\": \"67f66c07-6e61-4026-ade5-7e782fad3a5d\",\n" +
            "          \"url\": \"https://www.last.fm/music/Foo+Fighters\"\n" +
            "        },\n" +
            "        \"image\": [\n" +
            "          {\n" +
            "            \"#text\": \"https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
            "            \"size\": \"small\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"#text\": \"https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
            "            \"size\": \"medium\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"#text\": \"https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
            "            \"size\": \"large\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"#text\": \"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
            "            \"size\": \"extralarge\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"@attr\": {\n" +
            "          \"rank\": \"0\"\n" +
            "        }\n" +
            "      }"
    @Before
    fun createDb() {

        track= Track("009f7e7b-5ecf-4cef-a2c1-0f0c736e807e","Everlong","323","1368885","https://www.last.fm/music/Foo+Fighters/_/Everlong",
            listOf(Image("https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png","small"),
                Image("https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png","medium"),
                Image("https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png","large"),
                Image("https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png","extralarge")
            ), Artist("009f7e7b-5ecf-4cef-a2c1-0f0c736e807e","Foo Fighters","https://www.last.fm/music/Foo+Fighters/_/Everlong")
        )
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, LastFmDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        lastFmDao = db.lastFmDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTracks() = runBlocking{
        lastFmDao.insert(listOf(track))
        val factory = lastFmDao.listOfTracks("%")
        val extractedList = (factory.create() as LimitOffsetDataSource).loadRange(0, 10).first()
        assertEquals(true, track.mbid==extractedList.mbid)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetArtist()= runBlocking {
        lastFmDao.insert(listOf(track))
        val factory = lastFmDao.listOfTracks("%")
        val extractedList = (factory.create() as LimitOffsetDataSource).loadRange(0, 10).first()
        assertEquals(true, track.mbid==extractedList.mbid)
    }
}
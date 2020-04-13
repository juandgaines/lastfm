package com.example.lasftfm.repository.db

import com.example.lasftfm.repository.network.Artist
import com.example.lasftfm.repository.network.Artist2
import com.example.lasftfm.repository.network.Image
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DataConverterTest {

    private lateinit var dataConverter: DataConverter
    private lateinit var jsonString: String
    private lateinit var jsonArtist: String
    private lateinit var jsonArtistFromTrack: String

    @Before
    fun initialize() {
        dataConverter = DataConverter()
        jsonString =
            "[{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"small\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"medium\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"large\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"extralarge\"}]"
        jsonArtist = "{\n" +
                "        \"name\": \"Radiohead\",\n" +
                "        \"listeners\": \"4847833\",\n" +
                "        \"mbid\": \"a74b1b7f-71a5-4011-9441-d0b5e4122711\",\n" +
                "        \"url\": \"https://www.last.fm/music/Radiohead\",\n" +
                "        \"streamable\": \"0\",\n" +
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
                "          },\n" +
                "          {\n" +
                "            \"#text\": \"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
                "            \"size\": \"mega\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }"

        jsonArtistFromTrack =
            "{\"mbid\":\"67f66c07-6e61-4026-ade5-7e782fad3a5d\",\"name\":\"Foo Fighters\",\"url\":\"https://www.last.fm/music/Foo+Fighters\"}"
    }

    @Test
    fun stringToTracksImagesList_convertFullObjectFromJson_returnsArrayWithSize() {
        val list = dataConverter.stringToTracksImagesList(jsonString)
        assertEquals(true, list.size > 0)
    }

    @Test
    fun imagesListToString_convertListOfImage_returnsSameArrayInJsonString() {

        val list = listOf(
            Image(
                "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                "small"
            ),
            Image(
                "https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png",
                "medium"
            ),
            Image(
                "https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png",
                "large"
            ),
            Image(
                "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                "extralarge"
            )
        )
        println(jsonString)
        println(dataConverter.imagesListToString(list))
        assertEquals(true, jsonString == dataConverter.imagesListToString(list))

    }

    @Test
    fun stringToArtis_convertArtist_returnNameOfObjectOnJsonArtist() {
        val artist = dataConverter.stringToArtis(jsonArtist)
        assertEquals(true, artist.name == "Radiohead")
    }

    @Test
    fun artistToString_convertArtist_returnNameOfObjectOnJsonArtist() {
        val artist = dataConverter.artistToString(
            Artist(
                name = "Foo Fighters",
                mbid = "67f66c07-6e61-4026-ade5-7e782fad3a5d",
                url = "https://www.last.fm/music/Foo+Fighters"

            )
        )
        println(artist)
        println(jsonArtistFromTrack)
        assertEquals(true, jsonArtistFromTrack.trim() == artist.trim())
    }

    @Test
    fun stringToArtist2_convertArtist2_returnNameOfObjectOnJsonArtist2() {
        val artist = dataConverter.stringToArtist2(jsonArtist)
        assertEquals(true, artist.name == "Radiohead")
    }

    @Test
    fun testArtistToString_convertJsonFromJsonArtist_returnStringJsonWithArtistName() {

        val string = dataConverter.artistToString(
            Artist2(
                name = "Radiohead",
                listeners = "4847833",
                mbid = "a74b1b7f-71a5-4011-9441-d0b5e4122711",
                url = "https://www.last.fm/music/Radiohead",
                streamable = "0",
                image = listOf(
                    Image(
                        "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
                        "small"
                    )
                )
            )
        )
        assertEquals(true, string.contains("Radiohead"))
    }
}
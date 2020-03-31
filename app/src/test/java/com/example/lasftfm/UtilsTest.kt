package com.example.lasftfm

import org.junit.Assert.*
import org.junit.Test

class UtilsTest{

    @Test
    fun getQueryString(){
        assertEquals("%",Utils.convertToQuery(null))
        assertEquals("%Blinding lights%",Utils.convertToQuery("Blinding lights"))
        assertEquals("% %",Utils.convertToQuery(" "))
    }
}
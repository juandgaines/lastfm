package com.example.lasftfm

object Utils {

        fun convertToQuery(query: String?):String{
            return if (query == null) {
                "%"
            } else {
                "%${query}%"
            }
        }
}
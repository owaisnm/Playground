package com.owais.playground

import java.text.ParseException
import java.text.SimpleDateFormat

object Utils {

    @JvmStatic
    fun dateToMilli(dateString: String, dateFormat: String): Long {
        val sdf = SimpleDateFormat(dateFormat)
        try {
            val date = sdf.parse(dateString)
            return date.time
        } catch (exception: ParseException) {
            return 0
        }
    }
}
package com.owais.playground

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    @JvmStatic
    fun getDate(milliSeconds: Long, dateFormat: String): String {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.getTime())
    }

}
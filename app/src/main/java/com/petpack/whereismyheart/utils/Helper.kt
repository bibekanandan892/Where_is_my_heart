package com.petpack.whereismyheart.utils

import android.content.Context
import java.util.*

fun getTime(timeInMillis: Long): String {
    return try {
        // create a Date object from the timestamp
        val date = Date(timeInMillis)

// create a Calendar instance and set the date to the above Date object
        val calendar = Calendar.getInstance().apply {
            time = date
        }

// extract the year, month, day, hour, and minute from the Calendar instance
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        String.format(
            "%d:%02d %s",
            if (hour > 12) hour - 12 else hour,
            minute,
            if (hour >= 12) "PM" else "AM"
        )
    } catch (
        e: java.lang.Exception,
    ) {
        "NA"
    }


}
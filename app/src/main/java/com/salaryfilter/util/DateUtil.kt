package com.salaryfilter.util

import com.salaryfilter.data.network.DateTimeTypeAdapter.Companion.DATE_FORMAT
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Max Makeychik on 18.08.2016.
 */
object DateUtil {

    private val TAG = DateUtil::class.java.simpleName

    fun getDateTimeFromTimestamp(timestamp: Long): DateTime {
        return DateTime(timestamp * 1_000)
    }

    fun getTimeString(dateTime: DateTime): String {
        return DateTimeFormat.forPattern("HH:mm").print(dateTime)
    }

    fun getMonthYear(date: DateTime): String {
        val format = SimpleDateFormat("LLL yyyy", Locale.getDefault())
        return format.format(date.toDate())
    }

    fun getFullMonthYear(date: DateTime): String {
        val format = SimpleDateFormat("LLLL yyyy", Locale.getDefault())
        return format.format(date.toDate())
    }

    fun getDateTimeFromString(date: String): DateTime {
        return DateTimeFormat.forPattern(DATE_FORMAT).parseDateTime(date)
    }

    fun getDateForListScreen(date: DateTime): String {
        return DateTimeFormat.forPattern("EEE d MMM yyyy").print(date)
    }
}

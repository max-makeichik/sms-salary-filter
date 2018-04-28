package com.salaryfilter.data.db

import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime

object DateTimeConverter {

    @TypeConverter
    @JvmStatic
    fun toTimestamp(date: DateTime): Long {
        return date.millis
    }

    @TypeConverter
    @JvmStatic
    fun toDate(value: Long): DateTime {
        return DateTime(value)
    }
}
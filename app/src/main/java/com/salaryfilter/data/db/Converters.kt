package com.salaryfilter.data.db

import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): DateTime? {
        return if (value == null) null else DateTime(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: DateTime?): Long? {
        return date?.millis
    }
}
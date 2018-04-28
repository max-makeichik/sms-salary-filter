package com.salaryfilter.data.network

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.salaryfilter.util.DateUtil
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.io.IOException
import java.util.concurrent.TimeUnit

class DateTimeTypeAdapter : TypeAdapter<DateTime>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, dateTime: DateTime) {
        out.value(TimeUnit.MILLISECONDS.toSeconds(dateTime.millis))
    }

    @Throws(IOException::class)
    override fun read(reader: JsonReader): DateTime? {
        val token = reader.peek()
        if (token == JsonToken.STRING) {
            val dateTime = reader.nextString()
            val formatterShort = DateTimeFormat.forPattern(DATE_FORMAT_SHORT)
            val formatter = DateTimeFormat.forPattern(DATE_FORMAT)
            return if (dateTime.length <= 10) {
                formatterShort.parseDateTime(dateTime.substring(0, Math.min(DATE_FORMAT_SHORT.length, dateTime.length)))
            } else {
                formatter.parseDateTime(dateTime.substring(0, Math.min(DATE_FORMAT.length, dateTime.length)))
            }
        } else return if (token == JsonToken.NULL) {
            null
        } else {
            DateUtil.getDateTimeFromTimestamp(reader.nextLong())
        }
    }

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        const val DATE_FORMAT_SHORT = "yyyy-MM-dd"
    }
}
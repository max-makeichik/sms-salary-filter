package com.salaryfilter.domain.global.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.text.TextUtils
import com.salaryfilter.data.db.DateTimeConverter
import com.salaryfilter.util.DateUtil
import org.joda.time.DateTime

/**
 * Created by Max Makeychik on 20-Feb-18.
 */
@Entity
class Salary {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0
    @ColumnInfo(name = "text")
    var text: String = ""
    var sum: Double = 0.0
    var sumString: String = ""
    @TypeConverters(DateTimeConverter::class)
    var date: DateTime = DateTime()
    var address: String = ""
    var currency: String = ""

    constructor()

    /**
     * @param filterString - string to filter salary text with
     * @return true if sum has been set, false otherwise
     */
    internal fun setSum(filterString: String): Boolean {
        val startIndex = text.indexOf(filterString)
        if (TextUtils.isEmpty(text) || startIndex == -1) {
            return false
        }
        val substring: String = text.substring(startIndex + filterString.length, text.length)
                .trim().replace(" +".toRegex(), " ")
        val stringBuilder = StringBuilder()
        var lettersFound = false
        var digitsFound = false
        var sumCurrency = ""
        var sum = ""
        for (c in substring.toCharArray().iterator()) {
            if (c.isDigit() || c == '.' || c == ',') {
                digitsFound = true
                sum += c
                stringBuilder.append(c)
            } else if (c.isWhitespace()) {
                if (lettersFound && digitsFound) break
                stringBuilder.append(c)
            } else {
                lettersFound = true
                sumCurrency += c
                stringBuilder.append(c)
            }
        }
        this.currency = sumCurrency
        this.sumString = stringBuilder.toString()
        val sumDouble = sum.toDoubleOrNull()
        if (sumDouble != null) {
            this.sum = sumDouble
            return true
        }
        return false
    }

    fun filter(address: String, pattern: String): Boolean {
        return (address.isEmpty() || this.address == address) && text.toLowerCase().contains(pattern)
    }

    fun getListDateString(): String {
        return DateUtil.getFullMonthYear(date)
    }

    fun setDate(timestamp: Long) {
        this.date = DateTime(timestamp)
    }

    fun getMonth(): Int {
        return date.year + date.monthOfYear
    }

    fun addSum(salary: Salary) {
        this.sum += salary.sum
        this.sumString = sum.toString() + currency
    }

    override fun toString(): String {
        return "Salary(id=$id, text='$text', sum=$sum, sumString='$sumString', date=$date, address='$address', currency='$currency')"
    }


}

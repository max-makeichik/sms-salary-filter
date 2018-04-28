package com.salaryfilter.domain.global.model

import com.salaryfilter.util.DateUtil
import org.joda.time.DateTime

/**
 * Created by Max Makeychik on 02-Mar-18.
 */
data class SalaryDateSection(val salaries: ArrayList<Salary>, val date: DateTime = getDate(salaries)) {

    fun getDate(): String {
        return DateUtil.getFullMonthYear(date)
    }

    companion object {
        fun getDate(salaries: List<Salary>): DateTime {
            return salaries[0].date
        }
    }

    fun addSalary(salary: Salary) {
        salaries.add(salary)
    }
}
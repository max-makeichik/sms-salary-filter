package com.salaryfilter.data.repository.repository_impl

import com.salaryfilter.data.repository.repository_interface.ISalariesRepository
import com.salaryfilter.domain.global.model.Salary
import timber.log.Timber
import java.util.*

/**
 * Created by Max Makeychik on 21-Feb-18.
 */
class SalariesRepository : ISalariesRepository {

    init {
        Timber.d("Sal")
    }

    private var salaries: List<Salary> = ArrayList()
    private var salariesGroupedByMonth: List<Salary> = ArrayList()

    override fun setSalaryList(salaryList: List<Salary>) {
        salaries = ArrayList(salaryList)
        setSalariesGroupedByMonth()
    }

    private fun setSalariesGroupedByMonth() {
        val monthSalaryMap = TreeMap<Int, Salary>()
        for (salary in salaries) {
            val key = salary.getMonth()
            if (!monthSalaryMap.containsKey(key)) {
                monthSalaryMap[key] = salary
            } else {
                monthSalaryMap[key]!!.addSum(salary)
            }
        }
        salariesGroupedByMonth = monthSalaryMap.values.toList()
    }

    override fun getSalaries(groupedByMonth: Boolean): List<Salary> {
        Timber.d("groupedByMonth $groupedByMonth")
        val result = if (groupedByMonth) salariesGroupedByMonth else salaries
        Timber.d("result $result")
        return result
    }
}
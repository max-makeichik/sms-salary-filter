package com.salaryfilter.data.repository.repository_interface

import com.salaryfilter.domain.global.model.Salary

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
interface ISalariesRepository {
    fun setSalaryList(salaryList: List<Salary>)
    fun getSalaries(groupedByMonth: Boolean): List<Salary>
}
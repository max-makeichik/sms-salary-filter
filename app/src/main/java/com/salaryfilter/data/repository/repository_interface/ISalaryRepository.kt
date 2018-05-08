package com.salaryfilter.data.repository.repository_interface

import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.util.RxUtil
import io.reactivex.subjects.PublishSubject

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
interface ISalaryRepository {
    fun setSalaryList(salaryList: List<Salary>)
    fun getSalaries(groupedByMonth: Boolean): List<Salary>
    fun updateSalaries()
    fun getUpdateSalariesSubject(): PublishSubject<RxUtil.Irrelevant>
    fun hasCachedSalaries(): Boolean
    fun getCachedSalaries(): List<Salary>
}
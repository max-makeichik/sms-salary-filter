package com.salaryfilter.domain.model_interface

import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.util.RxUtil
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
interface ISalaryListModel {
    fun getSalaryListOneByOne(): Flowable<Salary>
    fun getSalary(): Salary
    fun setSalary(salary: Salary)
    fun getUpdateSalariesSubject(): PublishSubject<RxUtil.Irrelevant>
    fun getCachedSalaries(): List<Salary>
    fun hasCachedSalaries(): Boolean
}
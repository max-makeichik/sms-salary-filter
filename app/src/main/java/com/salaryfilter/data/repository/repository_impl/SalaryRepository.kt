package com.salaryfilter.data.repository.repository_impl

import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import com.salaryfilter.data.repository.repository_interface.ISalaryRepository
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.util.RxUtil
import io.reactivex.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

/**
 * Created by Max Makeychik on 21-Feb-18.
 */
class SalaryRepository @Inject constructor(private val prefsRepository: IPrefsRepository): ISalaryRepository {

    private var salaries: List<Salary> = ArrayList()
    private var salariesGroupedByMonth: List<Salary> = ArrayList()

    private val updateSalariesSubject = PublishSubject.create<RxUtil.Irrelevant>()

    override fun getCachedSalaries(): List<Salary> {
        return getSalaries(prefsRepository.isGroupByMonth())
    }

    override fun hasCachedSalaries(): Boolean {
        return getSalaries(prefsRepository.isGroupByMonth()).isNotEmpty()
    }

    override fun getUpdateSalariesSubject(): PublishSubject<RxUtil.Irrelevant> {
        return updateSalariesSubject
    }

    override fun updateSalaries() {
        getUpdateSalariesSubject().onNext(RxUtil.Irrelevant.INSTANCE)
    }

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
        return if (groupedByMonth) salariesGroupedByMonth else salaries
    }
}
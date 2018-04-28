package com.salaryfilter.util.graph

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.util.DateUtil
import org.joda.time.DateTime

class GraphXAxisValueFormatter(range: List<Salary>, private val mSlot: GraphTimeInterval, private val mInterval: Int) : IAxisValueFormatter {

    private val salaries: List<String>

    init {
        salaries = ArrayList()
        for (salary in range) {
            //Timber.d("Time : " + DateUtil.getMonthYear(salary.date))
            salaries.add(DateUtil.getMonthYear(salary.date))
        }
    }

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        //Timber.d("Value : " + value)
        return DateUtil.getMonthYear(DateTime(value.toLong()))
    }

}
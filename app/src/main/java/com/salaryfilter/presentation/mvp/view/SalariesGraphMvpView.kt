package com.salaryfilter.presentation.mvp.view

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.mikephil.charting.data.Entry
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.presentation.mvp.view.base.BaseMvpView
import java.util.*

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
interface SalariesGraphMvpView : BaseMvpView {
    fun showSalariesGraph(salaries: List<Salary>, entries: ArrayList<Entry>)
    fun showEmptyGraph()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showEmptySmsList()
}
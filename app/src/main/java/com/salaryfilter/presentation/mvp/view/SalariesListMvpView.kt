package com.salaryfilter.presentation.mvp.view

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.presentation.mvp.view.base.BaseMvpView

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
interface SalariesListMvpView : BaseMvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun addSalaryToList(salary: Salary)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun clearSalary()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSalariesAdded()
}
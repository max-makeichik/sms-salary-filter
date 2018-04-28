package com.salaryfilter.di.component

import com.salaryfilter.di.annotation.SalaryListScope
import com.salaryfilter.di.module.SalaryListModule
import com.salaryfilter.domain.model_impl.SalaryListModelImpl
import com.salaryfilter.presentation.mvp.presenter.MainSalariesPresenter
import com.salaryfilter.presentation.mvp.presenter.SalariesGraphPresenter
import com.salaryfilter.presentation.mvp.presenter.SalariesPresenter
import dagger.Subcomponent

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
@Subcomponent(modules = [SalaryListModule::class])
@SalaryListScope
interface SalaryListComponent {
    fun inject(salaryListPresenter: SalariesPresenter)
    fun inject(salaryModelImpl: SalaryListModelImpl)
    fun inject(salariesGraphPresenter: SalariesGraphPresenter)
    fun inject(mainSalariesPresenter: MainSalariesPresenter)
}
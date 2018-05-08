package com.salaryfilter.presentation.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.salaryfilter.App
import com.salaryfilter.data.repository.repository_interface.ISalaryRepository
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.presentation.mvp.presenter.base.BasePresenter
import com.salaryfilter.presentation.mvp.view.SalariesMvpView
import javax.inject.Inject

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
@InjectViewState
class MainSalariesPresenter : BasePresenter<SalariesMvpView>() {

    @Inject
    lateinit var salaryRepository: ISalaryRepository
    @Inject
    lateinit var resourceManager: IResourceManager

    init {
        App.salaryListComponent.inject(this)
    }

    fun updateSalaries() {
        salaryRepository.updateSalaries()
    }

}
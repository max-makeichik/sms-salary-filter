package com.salaryfilter.presentation.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.salaryfilter.App
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.domain.model_interface.ISalaryListModel
import com.salaryfilter.presentation.mvp.presenter.base.BasePresenter
import com.salaryfilter.presentation.mvp.view.SalariesMvpView
import com.salaryfilter.util.RxUtil
import javax.inject.Inject

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
@InjectViewState
class MainSalariesPresenter : BasePresenter<SalariesMvpView>() {

    @Inject
    lateinit var salaryModel: ISalaryListModel
    @Inject
    lateinit var resourceManager: IResourceManager

    init {
        App.salaryListComponent.inject(this)
    }

    fun updateSalaries() {
        salaryModel.getUpdateSalariesSubject().onNext(RxUtil.Irrelevant.INSTANCE)
    }

}
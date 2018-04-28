package com.salaryfilter.presentation.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.salaryfilter.App
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.presentation.mvp.view.PrefsMvpView
import javax.inject.Inject

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
@InjectViewState
class PrefsPresenter : MvpPresenter<PrefsMvpView>() {

    @Inject
    lateinit var resourceManager: IResourceManager

    init {
        App.appComponent.inject(this)
    }

    fun onSalaryChange() {
        viewState.setResultOk()
    }
}
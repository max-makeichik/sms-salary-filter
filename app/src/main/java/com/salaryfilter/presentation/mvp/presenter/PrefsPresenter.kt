package com.salaryfilter.presentation.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.salaryfilter.App
import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.presentation.mvp.view.PrefsMvpView
import javax.inject.Inject

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
@InjectViewState
class PrefsPresenter : MvpPresenter<PrefsMvpView>() {

    @Inject
    lateinit var prefsRepository: IPrefsRepository
    @Inject
    lateinit var resourceManager: IResourceManager

    init {
        App.appComponent.inject(this)
    }

    fun onSalaryChange() {
        viewState.setResultOk()
    }

    fun isGroupByMonth(): Boolean {
        return prefsRepository.isGroupByMonth()
    }

    fun putGroupByMonth(groupByMonth: Boolean) {
        prefsRepository.putGroupByMonth(groupByMonth)
    }

    fun getAddress(): String {
        return prefsRepository.getAddress()
    }

    fun putAddress(address: String) {
        prefsRepository.putAddress(address)
    }

    fun getSalaryFilterPattern(): String {
        return prefsRepository.getSalaryFilterPattern()
    }

    fun putSalaryFilterPattern(pattern: String) {
        prefsRepository.putSalaryFilterPattern(pattern)
    }


}
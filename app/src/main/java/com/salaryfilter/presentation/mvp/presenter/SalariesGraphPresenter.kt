package com.salaryfilter.presentation.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.EntryXComparator
import com.salaryfilter.App
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.domain.model_interface.ISalaryListModel
import com.salaryfilter.presentation.mvp.presenter.base.BasePresenter
import com.salaryfilter.presentation.mvp.view.SalariesGraphMvpView
import com.salaryfilter.util.exception.EmptySalaryException
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
@InjectViewState
class SalariesGraphPresenter : BasePresenter<SalariesGraphMvpView>() {

    @Inject
    lateinit var salaryModel: ISalaryListModel
    @Inject
    lateinit var resourceManager: IResourceManager

    init {
        App.salaryListComponent.inject(this)
        disposables.add(salaryModel.getUpdateSalariesSubject().subscribe {
            updateSalaries()
        })
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (salaryModel.hasCachedSalaries()) {
            buildSalaryListGraph()
        } else {
            updateSalaries()
        }
    }

    private fun updateSalaries() {
        viewState.showLoading()
        salaryModel.getSalaryListOneByOne()
                .doOnSubscribe {
                    viewState.showLoading()
                }
                .doFinally { viewState.hideLoading() }
                .subscribe(
                        {}
                        , { t ->
                    run {
                        if (t is EmptySalaryException) {
                            viewState.showEmptySmsList()
                        } else {
                            onError(t, "")
                        }
                    }
                },
                        {
                            buildSalaryListGraph()
                        }
                )
    }

    private fun buildSalaryListGraph() {
        val salaryList = salaryModel.getCachedSalaries()
        val entries = ArrayList<Entry>()
        Timber.d("salaryList $salaryList")
        salaryList.mapTo(entries) { Entry(it.date.millis.toFloat(), it.sum.toFloat()) }
        if (!salaryList.isEmpty()) {
            entries.sortWith(EntryXComparator())
            viewState.showSalariesGraph(salaryList, entries)
        } else {
            viewState.showEmptyGraph()
        }
    }

}
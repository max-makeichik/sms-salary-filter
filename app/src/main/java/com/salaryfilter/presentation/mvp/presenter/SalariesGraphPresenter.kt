package com.salaryfilter.presentation.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.EntryXComparator
import com.salaryfilter.App
import com.salaryfilter.data.repository.repository_interface.ISalaryRepository
import com.salaryfilter.domain.interactor_interface.IGetSalariesUseCase
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.presentation.mvp.presenter.base.BasePresenter
import com.salaryfilter.presentation.mvp.view.SalariesGraphMvpView
import com.salaryfilter.util.exception.EmptySalaryException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
@InjectViewState
class SalariesGraphPresenter : BasePresenter<SalariesGraphMvpView>() {

    @Inject
    lateinit var salaryRepository: ISalaryRepository
    @Inject
    lateinit var getSalariesUseCase: IGetSalariesUseCase
    @Inject
    lateinit var resourceManager: IResourceManager

    init {
        App.salaryListComponent.inject(this)
        disposables.add(salaryRepository.getUpdateSalariesSubject().subscribe {
            updateSalaries()
        })
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (salaryRepository.hasCachedSalaries()) {
            buildSalaryListGraph()
        } else {
            updateSalaries()
        }
    }

    private fun updateSalaries() {
        disposables.add(getSalariesUseCase.getSalaryListOneByOne()
                .doOnSubscribe { viewState.showLoading() }
                .doFinally { viewState.hideLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {},
                        { t ->
                            run {
                                if (t is EmptySalaryException) {
                                    viewState.showEmptySmsList()
                                } else {
                                    onError(t, "")
                                }
                            }
                        },
                        { buildSalaryListGraph() }
                ))
    }

    private fun buildSalaryListGraph() {
        val salaryList = salaryRepository.getCachedSalaries()
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
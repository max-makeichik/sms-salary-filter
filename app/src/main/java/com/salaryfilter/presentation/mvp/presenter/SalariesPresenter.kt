package com.salaryfilter.presentation.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.salaryfilter.App
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.domain.model_interface.ISalaryListModel
import com.salaryfilter.presentation.mvp.presenter.base.BasePresenter
import com.salaryfilter.presentation.mvp.view.SalariesListMvpView
import com.salaryfilter.util.exception.EmptySalaryException
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
@InjectViewState
class SalariesPresenter : BasePresenter<SalariesListMvpView>() {

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

    override fun attachView(view: SalariesListMvpView) {
        super.attachView(view)
        Timber.d("attachView")
    }

    fun updateSalaries() {
        disposables.add(salaryModel.getSalaryListOneByOne()
                .doOnSubscribe {
                    viewState.clearSalary()
                    viewState.showLoading()
                }
                .doFinally { viewState.hideLoading() }
                .subscribe(
                        { salary ->
                            viewState.addSalaryToList(salary)
                            viewState.hideLoading()
                        },
                        { t ->
                            run {
                                if (t is EmptySalaryException) {
                                    viewState.showEmptySmsList()
                                } else {
                                    onError(t, "")
                                }
                            }
                        },
                        {
                            viewState.showSalariesAdded()
                        }
                ))
    }

}
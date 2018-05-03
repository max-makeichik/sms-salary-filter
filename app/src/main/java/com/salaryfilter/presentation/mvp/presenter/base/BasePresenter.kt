package com.salaryfilter.presentation.mvp.presenter.base

import com.arellomobile.mvp.MvpPresenter
import com.salaryfilter.presentation.mvp.view.base.BaseMvpView
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Max Makeychik on 29.06.17.
 */
open class BasePresenter<View : BaseMvpView> : MvpPresenter<View>() {

    protected var disposables = CompositeDisposable()

    protected fun onError(throwable: Throwable, errorMessage: String) {
        throwable.printStackTrace()
        viewState.showError(errorMessage)
        viewState.hideLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
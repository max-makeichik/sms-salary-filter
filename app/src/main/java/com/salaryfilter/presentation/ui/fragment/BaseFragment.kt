package com.salaryfilter.presentation.ui.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpFragment
import com.salaryfilter.presentation.mvp.view.base.BaseMvpView

abstract class BaseFragment : MvpFragment() {

    private lateinit var loadDataListener: BaseMvpView

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View? {
        return layoutInflater.inflate(getLayoutId(), viewGroup, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is BaseMvpView) {
            loadDataListener = activity as BaseMvpView
        } else {
            throw IllegalStateException("Activity must implement BaseMvpView")
        }
    }

    fun showLoading() {
        loadDataListener.showLoading()
    }

    open fun hideLoading() {
        loadDataListener.hideLoading()
    }

    fun showError(errorMessage: String) {
        loadDataListener.showError(errorMessage)
    }

    fun hideError() {
        loadDataListener.hideError()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

}

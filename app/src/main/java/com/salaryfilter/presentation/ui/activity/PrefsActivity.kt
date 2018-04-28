package com.salaryfilter.presentation.ui.activity

import android.app.Activity
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.salaryfilter.R
import com.salaryfilter.presentation.mvp.presenter.PrefsPresenter
import com.salaryfilter.presentation.mvp.view.PrefsMvpView
import com.salaryfilter.presentation.ui.fragment.PrefsFragment
import timber.log.Timber

/**
 * Created by Maksim Makeychik on 10.03.2018.
 */
class PrefsActivity: BaseActivity(), PrefsFragment.PrefsListener, PrefsMvpView {

    @InjectPresenter
    lateinit var prefsPresenter: PrefsPresenter

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_prefs)
        setToolbar(R.string.prefs)
        setBackIconVisible(true)
        replaceFragment(PrefsFragment())
    }

    override fun onSalaryChange() {
        prefsPresenter.onSalaryChange()
    }

    override fun setResultOk() {
        Timber.d("setResultOk: ")
        setResult(Activity.RESULT_OK)
    }

}
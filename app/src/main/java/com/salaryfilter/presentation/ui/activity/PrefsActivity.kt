package com.salaryfilter.presentation.ui.activity

import android.os.Bundle
import com.salaryfilter.R
import com.salaryfilter.presentation.ui.fragment.PrefsFragment

/**
 * Created by Maksim Makeychik on 10.03.2018.
 */
class PrefsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefs)
        setToolbar(R.string.prefs)
        setBackIconVisible(true)
        replaceFragment(PrefsFragment())
    }
}
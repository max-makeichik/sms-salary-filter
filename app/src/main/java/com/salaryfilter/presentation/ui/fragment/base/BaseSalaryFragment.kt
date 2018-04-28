package com.salaryfilter.presentation.ui.fragment.base

import android.os.Bundle
import com.salaryfilter.presentation.ui.fragment.BaseFragment

/**
 * Created by Max Makeychik on 28-Feb-18.
 */
abstract class BaseSalaryFragment: BaseFragment() {

    protected lateinit var menuListener: MenuListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(activity is MenuListener) {
            menuListener = activity as MenuListener
        } else {
            throw IllegalStateException("Activity must implement MenuListener")
        }
    }

    interface MenuListener {
        fun showPrefs()
    }
}
package com.salaryfilter.presentation.ui.activity

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.salaryfilter.R
import com.salaryfilter.presentation.ui.ErrorHandleDelegate
import com.salaryfilter.util.getName
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : MvpAppCompatActivity() {

    private val errorHandleDelegate = ErrorHandleDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        errorHandleDelegate.onCreate(this)
    }

    protected fun setToolbar(string: String) {
        setSupportActionBar(toolbar)
        toolbar.title = string
    }

    protected fun setToolbar(@StringRes stringResId: Int) {
        setSupportActionBar(toolbar)
        toolbar.setTitle(stringResId)
    }

    protected fun setBackIconVisible(visible: Boolean) {
        if (supportActionBar != null) {
            supportActionBar!!.setHomeButtonEnabled(visible) // disable the button
            supportActionBar!!.setDisplayHomeAsUpEnabled(visible) // remove the left caret
            supportActionBar!!.setDisplayShowHomeEnabled(visible) // remove the icon
            //supportActionBar!!.setHomeAsUpIndicator(if (visible) getBackArrowDrawable() else 0)
        }
    }

    fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    fun showError(errorMessage: String) {
        errorHandleDelegate.showError(errorMessage)
    }

    fun hideError() {
    }

    protected fun replaceFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_body_container, fragment, fragment.getName())
                .commit()
    }

    protected fun removeFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
    }

    protected fun removeFragmentStateLoss(fragment: Fragment) {
        fragmentManager.beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss()
    }

    protected fun replaceFragmentWithAnimation(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction
                .replace(R.id.main_body_container, fragment)
                .commit()
    }

    protected fun addFragment(fragment: Fragment) {
        if (!fragment.isAdded) {
            fragmentManager.beginTransaction()
                    .add(R.id.main_body_container, fragment, fragment.getName())
                    .commit()
        }
    }

    protected fun replaceFragmentAddingToBackStack(containerId: Int, animate: Boolean, fragment: Fragment) {
        replaceFragmentAddingToBackStack(containerId, animate, fragment, false)
    }

    protected fun replaceFragmentAddingToBackStackStateLoss(containerId: Int, animate: Boolean, fragment: Fragment) {
        replaceFragmentAddingToBackStack(containerId, animate, fragment, true)
    }

    private fun replaceFragmentAddingToBackStack(containerId: Int, animate: Boolean, fragment: Fragment, allowStateLoss: Boolean) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (animate) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
        fragmentTransaction
                .replace(containerId, fragment)
                .addToBackStack(null)
        if (allowStateLoss) {
            fragmentTransaction.commitAllowingStateLoss()
        } else {
            fragmentTransaction.commit()
        }
    }

    protected fun addFragmentAddingToBackStack(containerId: Int = R.id.main_body_container, animate: Boolean = true, fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (animate) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
        fragmentTransaction
                .add(containerId, fragment, fragment.getName())
                .addToBackStack(null)
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

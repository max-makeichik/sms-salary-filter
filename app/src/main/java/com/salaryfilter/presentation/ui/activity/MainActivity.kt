package com.salaryfilter.presentation.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kobakei.ratethisapp.RateThisApp
import com.salaryfilter.R
import com.salaryfilter.presentation.mvp.presenter.MainSalariesPresenter
import com.salaryfilter.presentation.mvp.view.SalariesMvpView
import com.salaryfilter.presentation.ui.adapter.MainTabsPagerAdapter
import com.salaryfilter.presentation.ui.fragment.SalaryGraphFragment
import com.salaryfilter.presentation.ui.fragment.base.BaseSalaryFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseTabsActivity(), BaseSalaryFragment.MenuListener, SalariesMvpView {

    @InjectPresenter
    lateinit var presenter: MainSalariesPresenter

    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar(R.string.app_name)
        setTabs()
        watchBackStack()

        setRateAppDialog()
    }

    private fun setRateAppDialog() {
        val config = RateThisApp.Config()
        config.apply {
            setTitle(R.string.rate_app_title)
            setMessage(R.string.rate_app_message)
            setYesButtonText(R.string.rate_app_yes)
            setNoButtonText(R.string.rate_app_no)
            setCancelButtonText(R.string.rate_app_cancel)
        }
        RateThisApp.init(config)
        RateThisApp.onCreate(this)
        RateThisApp.showRateDialogIfNeeded(this)
    }

    private fun watchBackStack() {
        fragmentManager.addOnBackStackChangedListener {
            val graphFragment = fragmentManager.findFragmentByTag(SalaryGraphFragment::class.java.simpleName)
            val isGraphVisible = graphFragment != null && graphFragment.isVisible
            setBackIconVisible(isGraphVisible)
            if (isGraphVisible) setTitle(R.string.graph_title) else setTitle(R.string.app_name)
            Handler().post { this.menu.findItem(R.id.action_salaries_graph).isVisible = !isGraphVisible }
        }
    }

    override fun showPrefs() {
        startActivityForResult(Intent(this, PrefsActivity::class.java), REQUEST_CODE_UPDATE_SALARY)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_salaries_graph -> {
                setBottomBarItemSelected(MainTabsPagerAdapter.POSITION_SALARIES_GRAPH)
                Handler().post {
                    this.menu.findItem(R.id.action_salaries_graph).isVisible = false
                    this.menu.findItem(R.id.action_salaries_list).isVisible = true
                }
                true
            }
            R.id.action_salaries_list -> {
                setBottomBarItemSelected(MainTabsPagerAdapter.POSITION_SALARIES_LIST)
                Handler().post {
                    this.menu.findItem(R.id.action_salaries_list).isVisible = false
                    this.menu.findItem(R.id.action_salaries_graph).isVisible = true
                }
                true
            }
            R.id.action_prefs -> {
                showPrefs()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_UPDATE_SALARY -> {
                if (resultCode == Activity.RESULT_OK) {
                    presenter.updateSalaries()
                }
            }
        }
    }

    override fun getViewPager(): ViewPager {
        return main_view_pager
    }

    companion object {
        const val REQUEST_CODE_UPDATE_SALARY = 0
    }
}

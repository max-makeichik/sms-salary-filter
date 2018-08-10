package com.salaryfilter.presentation.ui.fragment

import android.Manifest
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.salaryfilter.R
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.presentation.mvp.presenter.SalariesPresenter
import com.salaryfilter.presentation.mvp.view.SalariesListMvpView
import com.salaryfilter.presentation.ui.adapter.SalaryAdapter
import com.salaryfilter.presentation.ui.fragment.base.BaseSalaryFragment
import com.salaryfilter.util.PermissionUtil
import kotlinx.android.synthetic.main.fragment_salaries_list.*
import org.zakariya.stickyheaders.StickyHeaderLayoutManager

class SalaryListFragment : BaseSalaryFragment(), SalariesListMvpView {

    @InjectPresenter
    lateinit var presenter: SalariesPresenter

    private lateinit var adapter: SalaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PermissionUtil.hasReadSalaryPermissions(context!!)) {
            presenter.updateSalaries()
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_SMS), PERMISSION_REQUEST_CODE_READ_SMS)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SalaryAdapter(this.activity!!, mutableListOf(), object : SalaryAdapter.SalaryListener {
        })
        salary_list_recycler.apply {
            layoutManager = StickyHeaderLayoutManager()
            setEmptyView(salary_list_empty)
            salary_list_empty_prefs_button.setOnClickListener {
                menuListener.showPrefs()
            }
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = this@SalaryListFragment.adapter
        }
    }

    override fun clearSalary() {
        adapter.updateData(mutableListOf())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE_READ_SMS -> presenter.updateSalaries()
        }
    }

    override fun addSalaryToList(salary: Salary) {
        this.adapter.insertItem(salary)
        salary_list_recycler.changeVisibility()
    }

    override fun showSalariesAdded() {
        salary_list_recycler.changeVisibility()
    }

    override fun showEmptySmsList() {
        showError(getString(R.string.salaries_empty_message))
    }

    override fun getLayoutId() = R.layout.fragment_salaries_list

    companion object {
        const val PERMISSION_REQUEST_CODE_READ_SMS = 0
    }
}

package com.salaryfilter.presentation.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.salaryfilter.R
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.presentation.ui.holder.SalaryViewHolder
import org.zakariya.stickyheaders.SectioningAdapter
import timber.log.Timber

@Deprecated("Now is used SalaryAdapter with sections")
class SalaryOldAdapter(private val context: Activity, private val salaryList: MutableList<Salary>, private val listener: SalaryAdapter.SalaryListener) :
        SectioningAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SectioningAdapter.ViewHolder {
        return SalaryViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_salary, parent, false))
    }

    override fun onBindViewHolder(holder: SectioningAdapter.ViewHolder?, position: Int) {
        val holderItem = holder as SalaryViewHolder
        holderItem.bind(context, getItem(position), listener)
    }

    override fun getItemCount(): Int {
        return this.salaryList.size
    }

    fun insertItem(salary: Salary) {
        Timber.d("insertItem $salary")
        salaryList.add(salary)
        notifyItemInserted(getItemsCount() - 1)
    }

    fun updateData(salaryArr: List<Salary>?) {
        Timber.d("salaryArr $salaryArr")
        salaryList.clear()
        salaryList.addAll(salaryArr!!)
        notifyAllSectionsDataSetChanged()
    }

    private fun getItem(i: Int): Salary {
        return this.salaryList[i]
    }

    private fun getItemsCount(): Int {
        return salaryList.size
    }

    interface SalaryListener {
    }
}

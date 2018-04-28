package com.salaryfilter.presentation.ui.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import com.salaryfilter.R
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.presentation.ui.adapter.SalaryAdapter
import org.zakariya.stickyheaders.SectioningAdapter

class SalaryViewHolder(itemView: View) : SectioningAdapter.ItemViewHolder(itemView) {

    fun bind(context: Context, salary: Salary, listener: SalaryAdapter.SalaryListener) {
        val salaryName = itemView.findViewById<View>(R.id.salary_name) as TextView
        salaryName.text = salary.text
    }
}

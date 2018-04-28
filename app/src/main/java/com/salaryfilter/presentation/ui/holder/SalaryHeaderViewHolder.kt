package com.salaryfilter.presentation.ui.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import com.salaryfilter.R
import com.salaryfilter.domain.global.model.SalaryDateSection
import org.zakariya.stickyheaders.SectioningAdapter

class SalaryHeaderViewHolder(itemView: View) : SectioningAdapter.HeaderViewHolder(itemView) {

    fun bind(context: Context, dateSection: SalaryDateSection) {
        val dateName = itemView.findViewById<View>(R.id.date_name) as TextView
        dateName.text = dateSection.getDate()
    }
}

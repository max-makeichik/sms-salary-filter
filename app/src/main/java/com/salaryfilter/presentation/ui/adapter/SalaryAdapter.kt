package com.salaryfilter.presentation.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salaryfilter.R
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.domain.global.model.SalaryDateSection
import com.salaryfilter.presentation.ui.holder.SalaryHeaderViewHolder
import com.salaryfilter.presentation.ui.holder.SalaryViewHolder
import org.zakariya.stickyheaders.SectioningAdapter
import java.util.*

/**
 * Adapter for Salary items. Sorts them by date into sections with
 * month and year
 */
class SalaryAdapter(private val context: Activity, private val salaryList: MutableList<Salary>,
                    private val listener: SalaryListener) : SectioningAdapter() {

    private var sections = mutableListOf<SalaryDateSection>()
    private var sectionsMap = HashMap<String, SalaryDateSection>()

    fun updateData(salaries: List<Salary>) {
        salaryList.clear()
        salaryList.addAll(salaries)
        sections.clear()
        sectionsMap.clear()
        if (salaries.isEmpty()) {
            notifyAllSectionsDataSetChanged()
            return
        }
        // sort salaries into buckets by the date
        var date = salaries[0].getListDateString()
        var currentSection: SalaryDateSection? = null
        for (salary in salaries) {
            if (salary.getListDateString() != date) {
                if (currentSection != null) {
                    addSection(currentSection)
                }
                date = salary.getListDateString()
                currentSection = SalaryDateSection(ArrayList(), salary.date)
            }
            currentSection!!.addSalary(salary)
        }
        if (currentSection != null) {
            addSection(currentSection)
        }
        notifyAllSectionsDataSetChanged()
    }

    private fun addSection(section: SalaryDateSection): Int {
        sections.add(section)
        sectionsMap[section.getDate()] = section
        return sections.indexOf(section)
    }

    fun insertItem(salary: Salary) {
        salaryList.add(salary)
        val sectionIndex = addItem(salary)
        notifySectionDataSetChanged(sectionIndex)
    }

    private fun addItem(salary: Salary): Int {
        val section = sectionsMap[salary.getListDateString()]
        return if (section == null) {
            addSection(SalaryDateSection(arrayListOf(salary), salary.date))
        } else {
            section.addSalary(salary)
            sections.indexOf(section)
        }
    }

    override fun getNumberOfSections(): Int {
        return sections.size
    }

    override fun getNumberOfItemsInSection(sectionIndex: Int): Int {
        return sections[sectionIndex].salaries.size
    }

    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean {
        return true
    }

    override fun onCreateItemViewHolder(parent: ViewGroup?, itemType: Int): SectioningAdapter.ItemViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.item_salary, parent, false)
        return SalaryViewHolder(v)
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup?, headerType: Int): SectioningAdapter.HeaderViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.item_salary_header, parent, false)
        return SalaryHeaderViewHolder(v)
    }

    override fun onBindItemViewHolder(viewHolder: SectioningAdapter.ItemViewHolder?, sectionIndex: Int, itemIndex: Int, itemType: Int) {
        val section = sections[sectionIndex]
        val salaryViewHolder = viewHolder as SalaryViewHolder
        salaryViewHolder.bind(context, section.salaries[itemIndex], listener)
    }

    override fun onBindHeaderViewHolder(viewHolder: SectioningAdapter.HeaderViewHolder?, sectionIndex: Int, headerType: Int) {
        val section = sections[sectionIndex]

        viewHolder as SalaryHeaderViewHolder
        viewHolder.bind(context, section)
    }

    // TODO: 03-May-18 Fix of bug with support version = 27.1.0. Remove when stickyheaders library will be updated
    override fun onCreateGhostHeaderViewHolder(parent: ViewGroup): SectioningAdapter.GhostHeaderViewHolder {
        val ghostView = View(parent.context)
        ghostView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        return SectioningAdapter.GhostHeaderViewHolder(ghostView)
    }

    interface SalaryListener {
    }

}
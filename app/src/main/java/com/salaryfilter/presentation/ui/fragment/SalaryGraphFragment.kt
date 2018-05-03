package com.salaryfilter.presentation.ui.fragment

import android.support.v4.content.ContextCompat
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.salaryfilter.R
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.presentation.mvp.presenter.SalariesGraphPresenter
import com.salaryfilter.presentation.mvp.view.SalariesGraphMvpView
import com.salaryfilter.presentation.ui.fragment.base.BaseSalaryFragment
import com.salaryfilter.util.graph.GraphTimeInterval
import com.salaryfilter.util.graph.GraphUtil
import com.salaryfilter.util.graph.GraphXAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_salaries_graph.*
import java.util.*

class SalaryGraphFragment : BaseSalaryFragment(), SalariesGraphMvpView {
    @InjectPresenter
    lateinit var presenter: SalariesGraphPresenter

    override fun showEmptySmsList() {
        showError(getString(R.string.salaries_empty_message))
    }

    override fun hideLoading() {
        super.hideLoading()
        salaries_line_chart.visibility = View.VISIBLE
    }

    override fun showSalariesGraph(salaries: List<Salary>, entries: ArrayList<Entry>) {
        val dataSet = LineDataSet(entries, getString(R.string.salary) + " - " + salaries[0].currency)
        styleData(dataSet)
        styleAxis(salaries)
        salaries_line_chart.visibility = View.VISIBLE
        salaries_line_chart.apply {
            val description = Description()
            description.text = ""
            this.description = description
            data = LineData(dataSet)
            //legend.isEnabled = false
            invalidate()
            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry, h: Highlight) {
                }

                override fun onNothingSelected() {
                }
            })
        }
    }

    override fun showEmptyGraph() {
        salaries_line_chart.clear()
    }

    private fun styleAxis(salaries: List<Salary>) {
        val xAxis = salaries_line_chart.xAxis
        xAxis.apply {
            setDrawLabels(true)
            position = XAxis.XAxisPosition.TOP
            //setDrawGridLines(false)
            gridColor = ContextCompat.getColor(activity, R.color.graph_grid_vertical)
            textSize = 10f
            textColor = ContextCompat.getColor(activity, R.color.primary)
            valueFormatter = GraphXAxisValueFormatter(salaries, GraphTimeInterval.YEAR, 1)
        }
        val rightYAxis = salaries_line_chart.axisRight
        rightYAxis.apply {
            isEnabled = false
            axisMinimum = 0f
        }
        val leftYAxis = salaries_line_chart.axisLeft
        leftYAxis.apply {
            axisMinimum = 0f
        }
    }

    private fun styleData(dataSet: LineDataSet) {
        dataSet.apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            fillAlpha = 110
            circleColors = GraphUtil.getColors(activity, dataSet)
            lineWidth = 3f
            circleRadius = 3f
            setDrawCircleHole(false)
            valueTextSize = 11f
            setDrawFilled(true)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_salaries_graph
    }

}

package com.salaryfilter.util.graph

import android.content.Context
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.data.LineDataSet
import com.salaryfilter.R
import java.util.*

/**
 * Created by Max Makeychik on 28-Feb-18.
 */

object GraphUtil {

    /**
     * Returns colored max and min elements
     */
    fun getColors(context: Context, dataSet: LineDataSet): List<Int>? {
        val color = ContextCompat.getColor(context, R.color.graph_circle)
        val colorHigh = ContextCompat.getColor(context, R.color.graph_high_sum_circle)
        val colorLow = ContextCompat.getColor(context, R.color.graph_low_sum_circle)
        val colors = ArrayList<Int>()

        val entries = dataSet.values
        val maxSumEntry = entries.maxBy { it.y }
        val maxSums = entries.filter { it.y == maxSumEntry!!.y }

        // check if all items are the same
        if (maxSums.size == entries.size) {
            val intArray = IntArray(entries.size)
            intArray.fill(color)
            return intArray.asList()
        }

        val minSumEntry = entries.minBy { it.y }
        val minSums = entries.filter { it.y == minSumEntry!!.y }
        for (entry in entries) {
            when {
                maxSums.contains(entry) -> colors.add(colorHigh)
                minSums.contains(entry) -> colors.add(colorLow)
                else -> colors.add(color)
            }
        }
        return colors
    }
}
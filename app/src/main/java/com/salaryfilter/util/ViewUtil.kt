package com.salaryfilter.util

import android.app.Activity
import android.support.v4.content.res.ResourcesCompat
import android.util.TypedValue
import com.salaryfilter.R

/**
 * Created by Max Makeychik on 28-Feb-18.
 */
class ViewUtil {

    companion object {

        fun getBgColor(activity: Activity): Int {
            val a = TypedValue()
            activity.theme.resolveAttribute(android.R.attr.windowBackground, a, true)
            return if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                // windowBackground is a color
                a.data
            } else {
                return ResourcesCompat.getColor(activity.resources, R.color.bg, activity.theme)
            }
        }
    }

}
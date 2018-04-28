package com.salaryfilter.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by anton_tutarinov on 08.07.17.
 */

object KeyboardUtil {
    /**
     * Close software keyboard
     */
    fun closeSoftwareKeyboard(focusView: View?, context: Context?) {
        if (focusView != null && context != null) {
            val appCtx = context.applicationContext
            val inputManager = appCtx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(focusView.windowToken, 0)
        }

    }

    /**
     * Close software keyboard
     */
    fun closeSoftwareKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    /**
     * Open software keyboard
     */
    fun openSoftwareKeyboard(focusView: View?, context: Context?) {
        if (focusView != null && context != null) {
            val appCtx = context.applicationContext
            focusView.requestFocus()
            val inputManager = appCtx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(focusView, InputMethodManager.SHOW_FORCED)
        }
    }
}

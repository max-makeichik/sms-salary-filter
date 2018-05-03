package com.salaryfilter.presentation.ui

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

/**
 * Class delegated to handle showing errors
 *
 *
 * Created by Max Makeychik on 20-Mar-18.
 */

class ErrorHandleDelegate {

    private lateinit var context: Context

    fun onCreate(context: Context) {
        this.context = context
    }

    fun showError(errorMessage: String) {
        if (!TextUtils.isEmpty(errorMessage)) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}

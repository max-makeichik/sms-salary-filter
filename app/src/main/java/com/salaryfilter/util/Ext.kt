package com.salaryfilter.util

import android.app.Fragment

/**
 * Created by Maksim Makeychik on 08.03.2018.
 */

fun Fragment.getName(): String {
    return this::class.java.simpleName
}

fun <K, V> Map<K, V>.getKeyByValue(value: V): K? {
    for ((key, value1) in this) {
        if (value == value1) {
            return key
        }
    }
    return null
}
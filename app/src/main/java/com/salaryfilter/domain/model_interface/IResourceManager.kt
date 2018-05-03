package com.salaryfilter.domain.model_interface

import android.content.Context

interface IResourceManager {
    fun getString(resourceId: Int): String

    fun getInteger(resourceId: Int): Int
    fun getContext(): Context
}

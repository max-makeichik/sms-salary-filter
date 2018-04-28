package com.salaryfilter.domain.model_impl

import android.content.Context
import com.salaryfilter.domain.model_interface.IResourceManager
import javax.inject.Inject

class AndroidResourceManager @Inject
constructor(private val context: Context) : IResourceManager {

    override fun getString(resourceId: Int): String {
        return context.resources.getString(resourceId)
    }

    override fun getInteger(resourceId: Int): Int {
        return context.resources.getInteger(resourceId)
    }

}
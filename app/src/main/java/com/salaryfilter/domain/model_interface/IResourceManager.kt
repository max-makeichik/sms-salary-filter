package com.salaryfilter.domain.model_interface

interface IResourceManager {
    fun getString(resourceId: Int): String

    fun getInteger(resourceId: Int): Int
}

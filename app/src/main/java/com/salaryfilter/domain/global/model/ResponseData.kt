package com.salaryfilter.domain.global.model

open class ResponseData {
    var meta: Meta? = null

    inner class Meta {
        var errorCode: String? = null
        var errorMessage: String? = null
        var sid: String? = null
        var status: String? = null
        var timestamp: Long = 0
    }

    fun success(): Boolean {
        return if (this.meta == null || this.meta!!.status == null) {
            false
        } else this.meta!!.status!!.equals("Success", ignoreCase = true)
    }
}

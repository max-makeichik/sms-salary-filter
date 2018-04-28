package com.salaryfilter.data.repository.repository_interface

interface IPrefsRepository {
    fun isGroupByMonth(): Boolean
    fun putGroupByMonth(group: Boolean)
    fun getAddress(): String
    fun putAddress(address: String)
    fun getSalaryFilterPattern(): String
    fun putSalaryFilterPattern(pattern: String)
    fun putCurrency(currency: String)
}

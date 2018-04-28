package com.salaryfilter.data.repository.repository_impl

import android.content.Context
import android.content.SharedPreferences
import androidx.content.edit
import com.salaryfilter.R
import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import timber.log.Timber
import javax.inject.Inject

class PrefsRepositoryImpl @Inject
constructor(private val context: Context) : IPrefsRepository {

    private val userPrefs: SharedPreferences
    private val graphPrefs: SharedPreferences

    init {
        Timber.d("PrefsRepositoryImpl: ")
        userPrefs = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)
        graphPrefs = context.getSharedPreferences(GRAPH_PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun isGroupByMonth(): Boolean {
        return userPrefs.getBoolean(GRAPH_GROUP_BY_MONTH, true)
    }

    override fun putGroupByMonth(group: Boolean) {
        userPrefs.edit {
            putBoolean(GRAPH_GROUP_BY_MONTH, group)
        }
    }

    override fun getAddress(): String {
        return userPrefs.getString(SMS_ADDRESS, context.getString(R.string.prefs_default_address))
    }

    override fun putAddress(address: String) {
        userPrefs.edit {
            putString(SMS_ADDRESS, address)
        }
    }

    override fun getSalaryFilterPattern(): String {
        return userPrefs.getString(SALARY_PATTERN, context.getString(R.string.prefs_salary_filter))
    }

    override fun putSalaryFilterPattern(pattern: String) {
        userPrefs.edit {
            putString(SALARY_PATTERN, pattern)
        }
    }

    override fun putCurrency(currency: String) {
        userPrefs.edit {
            putString(CURRENCY, currency)
        }
    }

    companion object {
        private const val PREFS_PREFIX = "sms-filter"
        private const val USER_PREFS_NAME = PREFS_PREFIX + "_USER"
        private const val GRAPH_PREFS_NAME = PREFS_PREFIX + "_GRAPH"

        private const val GRAPH_GROUP_BY_MONTH = "graph.group_by_month"
        private const val SMS_ADDRESS = "sms_address"
        private const val SALARY_PATTERN = "salary_pattern"
        private const val CURRENCY = "currenncy"
    }

}

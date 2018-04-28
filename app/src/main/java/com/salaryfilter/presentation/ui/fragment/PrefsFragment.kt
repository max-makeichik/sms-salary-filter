package com.salaryfilter.presentation.ui.fragment

import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.EditTextPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.salaryfilter.App
import com.salaryfilter.R
import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import com.salaryfilter.util.ViewUtil
import javax.inject.Inject

class PrefsFragment : PreferenceFragment() {

    @Inject
    lateinit var prefsRepository: IPrefsRepository

    private lateinit var prefsListener: PrefsFragment.PrefsListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        addPreferencesFromResource(R.xml.prefs)

        val groupByMonthCheckbox = findPreference(getString(R.string.prefs_group_by_month_key)) as CheckBoxPreference
        groupByMonthCheckbox.apply {
            isChecked = prefsRepository.isGroupByMonth()
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
                prefsListener.onSalaryChange()
                groupByMonthCheckbox.isChecked = !groupByMonthCheckbox.isChecked
                prefsRepository.putGroupByMonth(value as Boolean)
                true
            }
        }
        // TODO: 02-Mar-18 set number empty forbid
        val addressEditTextPreference = findPreference(getString(R.string.prefs_address_key)) as EditTextPreference
        val address = prefsRepository.getAddress()
        addressEditTextPreference.apply {
            title = address
            setDefaultValue(address)
            dialogTitle = ""
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
                if (value.toString().isEmpty()) {
                    Toast.makeText(activity, getString(R.string.err_empty_address), Toast.LENGTH_SHORT).show()
                    false
                } else {
                    prefsListener.onSalaryChange()
                    title = value.toString()
                    prefsRepository.putAddress(value as String)
                    true
                }
            }
        }

        val salaryPatternEditTextPreference = findPreference(getString(R.string.prefs_salary_pattern_key)) as EditTextPreference
        val salaryFilterPattern = prefsRepository.getSalaryFilterPattern()
        salaryPatternEditTextPreference.apply {
            title = salaryFilterPattern
            dialogTitle = ""
            setDefaultValue(salaryFilterPattern)
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
                prefsListener.onSalaryChange()
                title = value.toString()
                prefsRepository.putSalaryFilterPattern(value as String)
                true
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view!!.setBackgroundColor(ViewUtil.getBgColor(activity))
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is PrefsListener) {
            prefsListener = activity as PrefsListener
        } else {
            throw IllegalStateException("Activity must implement PrefsListener")
        }
    }

    interface PrefsListener {
        fun onSalaryChange()
    }
}
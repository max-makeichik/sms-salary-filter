package com.salaryfilter.presentation.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.EditTextPreference
import android.preference.Preference
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.salaryfilter.R
import com.salaryfilter.presentation.mvp.presenter.PrefsPresenter
import com.salaryfilter.presentation.mvp.view.PrefsMvpView
import com.salaryfilter.presentation.ui.fragment.base.MvpPrefsFragment
import com.salaryfilter.util.ViewUtil

class PrefsFragment : MvpPrefsFragment(), PrefsMvpView {

    @InjectPresenter
    lateinit var prefsPresenter: PrefsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.prefs)

        val groupByMonthCheckbox = findPreference(getString(R.string.prefs_group_by_month_key)) as CheckBoxPreference
        groupByMonthCheckbox.apply {
            isChecked = prefsPresenter.isGroupByMonth()
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
                prefsPresenter.onSalaryChange()
                groupByMonthCheckbox.isChecked = !groupByMonthCheckbox.isChecked
                prefsPresenter.putGroupByMonth(value as Boolean)
                true
            }
        }
        // TODO: 02-Mar-18 set number empty forbid
        val addressEditTextPreference = findPreference(getString(R.string.prefs_address_key)) as EditTextPreference
        val address = prefsPresenter.getAddress()
        addressEditTextPreference.apply {
            title = address
            setDefaultValue(address)
            dialogTitle = ""
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
                if (value.toString().isEmpty()) {
                    Toast.makeText(activity, getString(R.string.err_empty_address), Toast.LENGTH_SHORT).show()
                    false
                } else {
                    prefsPresenter.onSalaryChange()
                    title = value.toString()
                    prefsPresenter.putAddress(value as String)
                    true
                }
            }
        }

        val salaryPatternEditTextPreference = findPreference(getString(R.string.prefs_salary_pattern_key)) as EditTextPreference
        val salaryFilterPattern = prefsPresenter.getSalaryFilterPattern()
        salaryPatternEditTextPreference.apply {
            title = salaryFilterPattern
            dialogTitle = ""
            setDefaultValue(salaryFilterPattern)
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
                prefsPresenter.onSalaryChange()
                title = value.toString()
                prefsPresenter.putSalaryFilterPattern(value as String)
                true
            }
        }
    }

    override fun setResultOk() {
        activity.setResult(Activity.RESULT_OK)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view!!.setBackgroundColor(ViewUtil.getBgColor(activity))
        return view
    }
}
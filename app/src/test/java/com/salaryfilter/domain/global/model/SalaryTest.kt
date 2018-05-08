package com.salaryfilter.domain.global.model

import android.text.TextUtils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.any
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner


/**
 * Created by Max Makeichik on 08-May-18.
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(TextUtils::class)
class SalaryTest {

    @Before
    fun setup() {
        PowerMockito.mockStatic(TextUtils::class.java)
        PowerMockito.`when`(TextUtils.isEmpty(any(CharSequence::class.java))).thenAnswer { invocation ->
            val a = invocation.arguments[0] as CharSequence
            a.isEmpty()
        }
    }

    @Test
    fun setSumWithSpaces() {
        val salary = Salary()
        salary.text = "Your account 'Save 1' was credited with 9 999 $ on Wed 22 Nov 2006"
        salary.setSum("with")
        assert(salary.currency == "$")
        assert(salary.sum == 9999.0)
        assert(salary.sumString == "9 999 $")
    }

    @Test
    fun setSumCurrencyBefore() {
        val salary = Salary()
        salary.text = "Your account 'Save 1' was credited with $ 999.99 on Wed 22 Nov 2006"
        salary.setSum("with")
        assert(salary.currency == "$")
        assert(salary.sum == 999.99)
        assert(salary.sumString == "$ 999.99")
    }

    @Test
    fun setSumCurrencyAfter() {
        val salary = Salary()
        salary.text = "Your account 'Save 1' was credited with 999.99$ on Wed 22 Nov 2006"
        salary.setSum("with")
        assert(salary.currency == "$")
        assert(salary.sum == 999.99)
        assert(salary.sumString == "999.99$")
    }


}
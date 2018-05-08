package com.salaryfilter.domain.interactor_impl

import android.database.Cursor
import android.net.Uri
import com.salaryfilter.R
import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import com.salaryfilter.data.repository.repository_interface.ISalaryRepository
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.domain.interactor_interface.IGetSalariesUseCase
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.util.exception.EmptySalaryException
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Max Makeichik on 08-May-18.
 */
class GetSalariesUseCase @Inject constructor(private val salaryRepository: ISalaryRepository,
                                             private val prefsRepository: IPrefsRepository,
                                             private val resourceManager: IResourceManager) : IGetSalariesUseCase {

    override fun getSalaryListOneByOne(): Flowable<Salary> {
        return Flowable.create({ emitter ->
            val cursor = resourceManager.getContext().contentResolver.query(Uri.parse("content://sms/inbox"),
                    null, null, null, null)
            val salaryList = ArrayList<Salary>()
            if (cursor.moveToFirst()) {
                val filterString = prefsRepository.getSalaryFilterPattern()
                val address = prefsRepository.getAddress()
                do {
                    val salary = getSalary(cursor)
                    if (!salary.filter(address, filterString)) {
                        continue
                    }
                    if (salary.setSum(filterString)) {
                        salaryList.add(salary)
                        emitter.onNext(salary)
                    }
                } while (cursor.moveToNext())
            } else {
                emitter.onError(EmptySalaryException(resourceManager.getString(R.string.salaries_empty_message)))
            }
            salaryRepository.setSalaryList(salaryList)
            emitter.onComplete()
            cursor.close()
        }, BackpressureStrategy.BUFFER)
    }

    private fun getSalary(cursor: Cursor): Salary {
        return Salary().apply {
            address = cursor.getString(cursor.getColumnIndex("address"))
            text = cursor.getString(cursor.getColumnIndex("body"))
            setDate(cursor.getLong(cursor.getColumnIndex("date")))
        }
    }


}

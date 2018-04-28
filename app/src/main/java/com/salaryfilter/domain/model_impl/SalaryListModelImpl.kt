package com.salaryfilter.domain.model_impl

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.salaryfilter.App
import com.salaryfilter.data.db.dao.SalariesDao
import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import com.salaryfilter.data.repository.repository_interface.ISalariesRepository
import com.salaryfilter.domain.global.model.Salary
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.domain.model_interface.ISalaryListModel
import com.salaryfilter.util.RxUtil
import com.salaryfilter.util.exception.EmptySalaryException
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Max Makeychik on 04-Dec-17.
 */
class SalaryListModelImpl(private val salariesRepository: ISalariesRepository, val prefsRepository: IPrefsRepository) : ISalaryListModel {

    @Inject
    lateinit var salaryDao: SalariesDao
    @Inject
    lateinit var context: Context
    @Inject
    lateinit var resourceManager: IResourceManager

    private var salary: Salary? = null
    private val updateSalariesSubject = PublishSubject.create<RxUtil.Irrelevant>()

    init {
        App.salaryListComponent.inject(this)
    }

    override fun getCachedSalaries(): List<Salary> {
        return salariesRepository.getSalaries(prefsRepository.isGroupByMonth())
    }

    override fun getSalaryListOneByOne(): Flowable<Salary> {
        Timber.d("getSalaryListOneByOne")
        val flowable = Flowable.create<Salary>({ emitter ->
            val cursor = context.contentResolver.query(Uri.parse("content://sms/inbox"),
                    null, null, null, null)
            val salaryList = ArrayList<Salary>()
            if (cursor.moveToFirst()) {
                do {
                    val salary = getSalary(cursor)
                    if (!salary.filter(prefsRepository.getAddress(), prefsRepository.getSalaryFilterPattern())) {
                        continue
                    }
                    if (salary.setSum(prefsRepository.getSalaryFilterPattern())) {
                        salaryList.add(salary)
                        emitter.onNext(salary)
                    }
                } while (cursor.moveToNext())
            } else {
                emitter.onError(EmptySalaryException())
            }
            salariesRepository.setSalaryList(salaryList)
            emitter.onComplete()
            cursor.close()
        }, BackpressureStrategy.BUFFER)
        return flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getSalary(cursor: Cursor): Salary {
        return Salary().apply {
            address = cursor.getString(cursor.getColumnIndex("address"))
            text = cursor.getString(cursor.getColumnIndex("body"))
            setDate(cursor.getLong(cursor.getColumnIndex("date")))
        }
    }

    override fun hasCachedSalaries(): Boolean {
        return salariesRepository.getSalaries(prefsRepository.isGroupByMonth()).isNotEmpty()
    }

    override fun getSalary(): Salary {
        return salary!!
    }

    override fun setSalary(salary: Salary) {
        this.salary = salary
    }

    override fun getUpdateSalariesSubject(): PublishSubject<RxUtil.Irrelevant> {
        return updateSalariesSubject
    }
}
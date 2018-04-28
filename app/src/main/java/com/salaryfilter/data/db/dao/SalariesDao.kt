package com.salaryfilter.data.db.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.salaryfilter.domain.global.model.Salary
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface SalariesDao {

    @Query("select * from salary")
    fun getAllSalaryList(): Flowable<List<Salary>>

    @Query("select * from salary where id = :id LIMIT 1")
    fun getSalaryById(id: Long): Maybe<Salary>

    @Insert(onConflict = REPLACE)
    fun insertSalary(salary: Salary): Long

    @Update(onConflict = REPLACE)
    fun updateSalary(salary: Salary)

    @Delete
    fun deleteSalary(salary: Salary)
}
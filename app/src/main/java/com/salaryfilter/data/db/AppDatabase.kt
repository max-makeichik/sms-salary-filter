package com.salaryfilter.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.salaryfilter.data.db.dao.SalariesDao
import com.salaryfilter.domain.global.model.Salary

@Database(entities = [(Salary::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class, DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun salaryDao(): SalariesDao
}
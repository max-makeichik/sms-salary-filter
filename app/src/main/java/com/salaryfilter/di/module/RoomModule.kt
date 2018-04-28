package com.salaryfilter.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.salaryfilter.data.db.AppDatabase
import dagger.Module
import dagger.Provides

/**
 * Created by Max Makeychik on 21-Dec-17.
 */

@Module
class RoomModule {

    @Provides
    fun providesAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()

    @Provides
    fun providesSalaryDao(database: AppDatabase) = database.salaryDao()

    companion object {
        val DB_NAME = "salary-db"
    }
}
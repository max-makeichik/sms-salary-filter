package com.salaryfilter.di.module

import android.content.Context
import com.salaryfilter.data.repository.repository_impl.PrefsRepositoryImpl
import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Andrey V. Murzin on 03.07.17.
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun getSharedPreferencesRepository(context: Context): IPrefsRepository {
        return PrefsRepositoryImpl(context)
    }

}

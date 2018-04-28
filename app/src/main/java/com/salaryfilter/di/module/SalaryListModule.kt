package com.salaryfilter.di.module

import com.salaryfilter.data.repository.repository_impl.SalariesRepository
import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import com.salaryfilter.data.repository.repository_interface.ISalariesRepository
import com.salaryfilter.di.annotation.SalaryListScope
import com.salaryfilter.domain.model_impl.SalaryListModelImpl
import com.salaryfilter.domain.model_interface.ISalaryListModel
import dagger.Module
import dagger.Provides

/**
 * Created by Andrey V. Murzin on 11.07.17.
 */

@Module
class SalaryListModule {
    @Provides
    @SalaryListScope
    fun getSalaryListModel(salariesRepository: ISalariesRepository, prefsRepository: IPrefsRepository): ISalaryListModel {
        return SalaryListModelImpl(salariesRepository, prefsRepository)
    }

    @Provides
    @SalaryListScope
    fun getSalaryRepository(): ISalariesRepository {
        return SalariesRepository()
    }
}
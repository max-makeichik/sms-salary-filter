package com.salaryfilter.di.module

import com.salaryfilter.data.repository.repository_impl.SalariesRepository
import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import com.salaryfilter.data.repository.repository_interface.ISalariesRepository
import com.salaryfilter.di.annotation.SalaryListScope
import com.salaryfilter.domain.model_impl.SalaryListModelImpl
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.domain.model_interface.ISalaryListModel
import dagger.Module
import dagger.Provides

/**
 * Created by Max Makeychik on 11.07.17.
 */

@Module
class SalaryListModule {
    @Provides
    @SalaryListScope
    fun getSalaryListModel(salariesRepository: ISalariesRepository, prefsRepository: IPrefsRepository,
                           resourceManager: IResourceManager): ISalaryListModel {
        return SalaryListModelImpl(salariesRepository, prefsRepository, resourceManager)
    }

    @Provides
    @SalaryListScope
    fun getSalaryRepository(): ISalariesRepository {
        return SalariesRepository()
    }
}
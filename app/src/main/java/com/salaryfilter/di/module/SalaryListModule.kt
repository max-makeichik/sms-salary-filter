package com.salaryfilter.di.module

import com.salaryfilter.data.repository.repository_impl.SalaryRepository
import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import com.salaryfilter.data.repository.repository_interface.ISalaryRepository
import com.salaryfilter.di.annotation.SalaryListScope
import com.salaryfilter.domain.interactor_impl.GetSalariesUseCase
import com.salaryfilter.domain.interactor_interface.IGetSalariesUseCase
import com.salaryfilter.domain.model_interface.IResourceManager
import dagger.Module
import dagger.Provides

/**
 * Created by Max Makeychik on 11.07.17.
 */

@Module
class SalaryListModule {

    @Provides
    @SalaryListScope
    fun provideGetSalariesUseCase(salaryRepository: ISalaryRepository, prefsRepository: IPrefsRepository,
                                  resourceManager: IResourceManager): IGetSalariesUseCase {
        return GetSalariesUseCase(salaryRepository, prefsRepository, resourceManager)
    }

    @Provides
    @SalaryListScope
    fun provideSalaryRepository(prefsRepository: IPrefsRepository): ISalaryRepository {
        return SalaryRepository(prefsRepository)
    }
}
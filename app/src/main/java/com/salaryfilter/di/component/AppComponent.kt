package com.salaryfilter.di.component

import com.salaryfilter.data.network.api_interface.ApiService
import com.salaryfilter.data.repository.repository_interface.IPrefsRepository
import com.salaryfilter.di.module.*
import com.salaryfilter.domain.model_interface.IResourceManager
import com.salaryfilter.presentation.mvp.presenter.PrefsPresenter
import com.salaryfilter.presentation.ui.fragment.PrefsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Max Makeychik on 26.07.17.
 */

@Component(modules = [RoomModule::class, ApiModule::class, AppModule::class, NetworkClientModule::class,
    RepositoryModule::class])
@Singleton
interface AppComponent {
    fun apiService(): ApiService

    fun prefsRepository(): IPrefsRepository

    fun resourceManager(): IResourceManager

    fun salaryListComponent(salaryListModule: SalaryListModule): SalaryListComponent
    fun inject(prefsFragment: PrefsFragment)
    fun inject(prefsPresenter: PrefsPresenter)

}

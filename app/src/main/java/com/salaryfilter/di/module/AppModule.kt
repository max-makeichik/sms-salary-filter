package com.salaryfilter.di.module

import android.content.Context
import com.salaryfilter.App
import com.salaryfilter.domain.model_impl.AndroidResourceManager
import com.salaryfilter.domain.model_interface.IResourceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    internal fun getResourceManager(context: Context): IResourceManager {
        return AndroidResourceManager(context)
    }

}
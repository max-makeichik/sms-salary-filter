package com.salaryfilter.di.module

import com.salaryfilter.data.network.api_impl.ApiServiceImpl
import com.salaryfilter.data.network.api_interface.ApiService
import com.salaryfilter.data.network.api_interface.INetworkClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Max Makeychik on 27.06.17.
 */
@Module
class ApiModule {
    @Provides
    @Singleton
    fun getApiInstance(networkClient: INetworkClient): ApiService {
        return ApiServiceImpl(networkClient)
    }
}

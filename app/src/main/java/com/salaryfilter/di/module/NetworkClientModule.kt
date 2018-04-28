package com.salaryfilter.di.module

import com.salaryfilter.data.network.api_impl.NetworkClientImpl
import com.salaryfilter.data.network.api_interface.INetworkClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Andrey V. Murzin on 10.07.17.
 */

@Module
class NetworkClientModule {
    internal val networkClient: INetworkClient
        @Provides
        @Singleton
        get() = NetworkClientImpl()
}

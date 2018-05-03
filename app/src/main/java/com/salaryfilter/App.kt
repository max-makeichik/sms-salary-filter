package com.salaryfilter

import android.app.Application
import com.salaryfilter.di.component.AppComponent
import com.salaryfilter.di.component.DaggerAppComponent
import com.salaryfilter.di.component.SalaryListComponent
import com.salaryfilter.di.module.AppModule
import com.salaryfilter.di.module.RoomModule
import com.salaryfilter.di.module.SalaryListModule
import io.fabric.sdk.android.Fabric
import io.reactivex.plugins.RxJavaPlugins
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber

/**
 * Created by Max Makeychik on 31-Jan-18.
 */
class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var salaryListComponent: SalaryListComponent
    }

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        Timber.plant(Timber.DebugTree())

        buildAppComponent()
        buildSalaryComponent()

        RxJavaPlugins.setErrorHandler { e ->
            Fabric.getLogger().log(0, "App", e.message)
        }
    }

    private fun buildAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .roomModule(RoomModule())
                .build()
    }

    private fun buildSalaryComponent() {
        salaryListComponent = appComponent.salaryListComponent(SalaryListModule())
    }
}
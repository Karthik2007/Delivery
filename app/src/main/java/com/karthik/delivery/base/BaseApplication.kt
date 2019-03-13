package com.karthik.delivery.base

import android.app.Application
import com.karthik.delivery.base.di.AppComponent
import com.karthik.delivery.base.di.DaggerAppComponent


/**
 * created by Karthik A on 09/03/19
 */
class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        appComponent.injectAppDependencies(this)

    }
}
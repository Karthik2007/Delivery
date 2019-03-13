package com.karthik.delivery.base.di

import com.karthik.delivery.base.BaseApplication
import com.karthik.delivery.base.navigation.AppNavigator
import com.karthik.delivery.base.network.ApiConfiguration
import com.karthik.delivery.base.network.ConnectionHandler
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


/**
 * AppComponent level DI withe Base modules
 *
 * created by Karthik A on 09/03/19
 */
@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    val apiConfiguration: ApiConfiguration

    val baseApplication: BaseApplication

    val appNavigator: AppNavigator

    val connectionHandler: ConnectionHandler

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(baseApplication: BaseApplication): Builder

        fun build(): AppComponent
    }

    fun injectAppDependencies(baseApplication: BaseApplication)

}
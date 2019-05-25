package com.karthik.delivery.base.di

import android.app.Application
import android.content.Context
import com.karthik.delivery.base.BaseApplication
import com.karthik.delivery.base.db.DeliveryDatabase
import com.karthik.delivery.base.navigation.AppNavigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 *
 * created by Karthik A on 09/03/19
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun providesAppNavigator(): AppNavigator = AppNavigator()

    @Singleton
    @Provides
    fun providesDatabase(baseApplication: BaseApplication): DeliveryDatabase = DeliveryDatabase.getDatabase(baseApplication)


}
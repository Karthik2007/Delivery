package com.karthik.delivery.base.di

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

    @Singleton
    @Provides
    fun providesAppNavigator(): AppNavigator = AppNavigator()

}
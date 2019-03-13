package com.karthik.delivery.base.di

import com.karthik.delivery.base.BaseApplication
import com.karthik.delivery.base.network.ApiConfiguration
import com.karthik.delivery.base.network.ConnectionHandler
import com.karthik.delivery.base.network.interceptor.BaseInterceptor
import com.karthik.delivery.base.network.interceptor.CacheInterceptor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 *
 * Provides network related object injection
 * created by Karthik A on 09/03/19
 */
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesConnectionHandler(baseApplication: BaseApplication): ConnectionHandler {
        return ConnectionHandler(baseApplication.applicationContext)
    }

    @Singleton
    @Provides
    fun providesBaseInterceptor() = BaseInterceptor()


    @Singleton
    @Provides
    fun providesCacheInterceptor(connectionHandler: ConnectionHandler): CacheInterceptor {
        return CacheInterceptor(connectionHandler)
    }

    @Singleton
    @Provides
    fun providesApiConfiguration(
        baseApplication: BaseApplication,
        baseInterceptor: BaseInterceptor,
        cacheInterceptor: CacheInterceptor
    ): ApiConfiguration {
        return ApiConfiguration(baseApplication.applicationContext, baseInterceptor, cacheInterceptor)
    }

}
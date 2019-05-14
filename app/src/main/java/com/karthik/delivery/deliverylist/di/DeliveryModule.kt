package com.karthik.delivery.deliverylist.di

import com.karthik.delivery.base.db.DeliveryDatabase
import com.karthik.delivery.base.network.ApiConfiguration
import com.karthik.delivery.base.network.ConnectionHandler
import com.karthik.delivery.deliverylist.data.api.DeliveryApi
import com.karthik.delivery.deliverylist.data.repo.DeliveryRepository
import dagger.Module
import dagger.Provides


/**
 * created by Karthik A on 11/03/19
 */
@Module
class DeliveryModule {
    @Provides
    fun providesDeliveryApi(apiConfiguration: ApiConfiguration): DeliveryApi {
        return apiConfiguration.getApiInstance(DeliveryApi::class.java)
    }

    @Provides
    fun providesDeliveryRepository(deliveryApi: DeliveryApi, database: DeliveryDatabase, connectionHandler: ConnectionHandler): DeliveryRepository {
        return DeliveryRepository(deliveryApi, database, connectionHandler)
    }
}
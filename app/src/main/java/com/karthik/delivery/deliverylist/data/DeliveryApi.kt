package com.karthik.delivery.deliverylist.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 *
 * created by Karthik A on 09/03/19
 */
interface DeliveryApi {

    @GET("/deliveries?limit=$ITEMS_PER_PAGE")
    fun getDeliveryList(@Query("offset") offset: Int): Call<List<Delivery>>


    private companion object {
        const val ITEMS_PER_PAGE = 20
    }
}
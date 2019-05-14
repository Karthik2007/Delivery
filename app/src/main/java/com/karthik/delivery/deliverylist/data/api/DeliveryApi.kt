package com.karthik.delivery.deliverylist.data.api

import com.karthik.delivery.deliverylist.data.Delivery
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 *
 * created by Karthik A on 09/03/19
 */
interface DeliveryApi {

    @GET("/deliveries")
    fun getDeliveryList(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<List<Delivery>>


}
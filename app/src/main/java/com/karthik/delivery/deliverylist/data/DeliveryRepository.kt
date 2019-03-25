package com.karthik.delivery.deliverylist.data

import com.karthik.delivery.base.network.ConnectionHandler
import com.karthik.delivery.base.util.Either
import com.karthik.delivery.base.util.Either.*
import com.karthik.delivery.base.util.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 *
 * Repository to fetch list of deliveries from server.
 * Coroutine on IO dispatcher is used to run network call on background thread
 *
 * created by Karthik A on 09/03/19
 */
class DeliveryRepository(
    private val deliveryApi: DeliveryApi,
    private val connectionHandler: ConnectionHandler
) {

    suspend fun getDeliveries(offset: Int): Either<Failure, DeliveryResponse> = withContext(Dispatchers.IO) {
        try {
            val response = deliveryApi.getDeliveryList(offset).execute()

            when (response.isSuccessful && response.body() != null) {
                true -> {
                    val returnedPage = (offset + DeliveryApi.ITEMS_PER_PAGE)/DeliveryApi.ITEMS_PER_PAGE // find out the returned page
                    val deliveryResponse = DeliveryResponse(returnedPage, response.body()!!)
                    Success(deliveryResponse)
                }
                false -> Error(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Error(Failure.ServerError)
        }
    }
}
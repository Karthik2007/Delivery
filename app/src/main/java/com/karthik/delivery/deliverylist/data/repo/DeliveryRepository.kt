package com.karthik.delivery.deliverylist.data.repo

import com.karthik.delivery.base.db.DeliveryDatabase
import com.karthik.delivery.base.network.ConnectionHandler
import com.karthik.delivery.base.util.Either
import com.karthik.delivery.base.util.Either.*
import com.karthik.delivery.base.util.Failure
import com.karthik.delivery.deliverylist.data.DeliveryResponse
import com.karthik.delivery.deliverylist.data.api.DeliveryApi
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
    private val deliveryDatabase: DeliveryDatabase,
    private val connectionHandler: ConnectionHandler
) {

    suspend fun getDeliveries(offset: Int): Either<Failure, DeliveryResponse> = withContext(Dispatchers.IO) {

        if(connectionHandler.isConnected)
        {
            //get data from api
            val response = getDeliveriesFromApi(offset) ?: return@withContext getDeliveriesFromDB(offset)


            //TODO handle if failure in inserting data to db
            //save the data into db
            saveDeliveries(response)
        }


        //retrieve the data from db
        return@withContext getDeliveriesFromDB(offset)
    }

    private fun saveDeliveries(response: DeliveryResponse)
    {
        deliveryDatabase.deliveryDao().insertAll(response.delivery)
    }

    private fun getDeliveriesFromDB(offset: Int): Either<Failure, DeliveryResponse>
    {
        var deliveries = deliveryDatabase.deliveryDao().get(offset, ITEMS_PER_PAGE)

        return if(deliveries.isEmpty()) {
            Error(Failure.ServerError)
        }else {
            val returnedPage = (offset + ITEMS_PER_PAGE)/ ITEMS_PER_PAGE
            Success(DeliveryResponse(returnedPage,deliveries))
        }
    }

    companion object {
        const val ITEMS_PER_PAGE = 20
    }


    private fun getDeliveriesFromApi(offset: Int): DeliveryResponse?
    {
        return try {
            val response = deliveryApi.getDeliveryList(offset, ITEMS_PER_PAGE).execute()

            when (response.isSuccessful && response.body() != null) {
                true -> {
                    val returnedPage = (offset + ITEMS_PER_PAGE)/ ITEMS_PER_PAGE // find out the returned page
                    val deliveryResponse =
                        DeliveryResponse(returnedPage, response.body()!!)
                    deliveryResponse
                }
                false -> null
            }
        } catch (exception: Throwable) {
            null
        }
    }
}
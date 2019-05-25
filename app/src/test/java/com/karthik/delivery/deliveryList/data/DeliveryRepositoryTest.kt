package com.karthik.delivery.deliveryList.data

import com.karthik.delivery.base.db.DeliveryDatabase
import com.karthik.delivery.base.network.ConnectionHandler
import com.karthik.delivery.base.util.Either
import com.karthik.delivery.base.util.Failure
import com.karthik.delivery.deliverylist.data.Delivery
import com.karthik.delivery.deliverylist.data.api.DeliveryApi
import com.karthik.delivery.deliverylist.data.repo.DeliveryRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response


/**
 * created by Karthik A on 11/03/19
 */
class DeliveryRepositoryTest
{
    private lateinit var deliveryRepository: DeliveryRepository

    //mocks
    private lateinit var mockDeliveryApi: DeliveryApi
    private lateinit var mockDeliveryDatabase: DeliveryDatabase
    private lateinit var mockConnectionHandler: ConnectionHandler
    private lateinit var mockDeliveryResponse: Response<List<Delivery>>
    private lateinit var mockDeliveryCall: Call<List<Delivery>>


    @Before
    fun setup()
    {
        mockDeliveryApi = mock()
        mockConnectionHandler = mock()
        mockDeliveryResponse = mock()
        mockDeliveryCall = mock()
        mockDeliveryDatabase = mock()

        deliveryRepository =
            DeliveryRepository(mockDeliveryApi, mockDeliveryDatabase, mockConnectionHandler)
    }


   /* @Test
    fun getDeliveries_whenSuccess()
    {

        var deliveryResponse: List<Delivery> = mock()
        given { mockConnectionHandler.isConnected }.willReturn(true)
        given { mockDeliveryApi.getDeliveryList(0,20) }.willReturn(mockDeliveryCall)
        given { mockDeliveryCall.execute() }.willReturn(mockDeliveryResponse)
        given { mockDeliveryResponse.body() }.willReturn(deliveryResponse)
        given { mockDeliveryResponse.isSuccessful }.willReturn(true)



        runBlocking {
            val response = deliveryRepository.getDeliveries(0)
            verify(mockDeliveryApi).getDeliveryList(0,20)
            response shouldEqual Either.Success(deliveryResponse)
        }

    }


    @Test
    fun getDeliveries_whenError()
    {
        given{ mockConnectionHandler.isConnected }.willReturn(true)
        given { mockDeliveryApi.getDeliveryList(0,20) }.willReturn(mockDeliveryCall)


        runBlocking {
            val response = deliveryRepository.getDeliveries(0)
            verify(mockDeliveryApi).getDeliveryList(0,20)
            response shouldEqual Either.Error(Failure.ServerError)
        }
    }

    @Test
    fun getDeliveries_whenException()
    {

        given{ mockConnectionHandler.isConnected }.willReturn(true)
        given { mockDeliveryApi.getDeliveryList(0,20) }.willReturn(mockDeliveryCall)
        given { mockDeliveryCall.execute() }.willReturn(mockDeliveryResponse)

        runBlocking {
            val response = deliveryRepository.getDeliveries(0)
            verify(mockDeliveryApi).getDeliveryList(0,20)
            response shouldEqual Either.Error(Failure.ServerError)
        }
    }
*/


}
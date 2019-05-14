package com.karthik.delivery.deliveryList.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.karthik.delivery.base.network.ConnectionHandler
import com.karthik.delivery.base.util.Either
import com.karthik.delivery.deliveryList.util.testLifecycleOwner
import com.karthik.delivery.deliverylist.data.Delivery
import com.karthik.delivery.deliverylist.data.DeliveryResponse
import com.karthik.delivery.deliverylist.data.repo.DeliveryRepository
import com.karthik.delivery.deliverylist.viewmodel.DeliveryListViewModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.internal.util.reflection.FieldSetter


/**
 * created by Karthik A on 11/03/19
 */
class DeliveryViewModelTest {


    @get: Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var deliveryListViewModel: DeliveryListViewModel

    //mocks

    private lateinit var mockRepository: DeliveryRepository
    private lateinit var mockConnectionHandler: ConnectionHandler
    private lateinit var mockFetchObserver: Observer<Boolean>
    private lateinit var mockNoInternetObserver: Observer<Boolean>
    private lateinit var mockOfflineDataObserver: Observer<Boolean>
    private lateinit var mockEmptyListObserver: Observer<Boolean>
    private lateinit var mockDeliveryResultObserver: Observer<DeliveryResponse>


    @Before
    fun setup() {
        mockEmptyListObserver = mock()
        mockRepository = mock()
        mockFetchObserver = mock()
        mockNoInternetObserver = mock()
        mockOfflineDataObserver = mock()
        mockDeliveryResultObserver = mock()
        mockConnectionHandler = mock()

        deliveryListViewModel = DeliveryListViewModel(mockRepository, mockConnectionHandler)

        deliveryListViewModel.emptyListView.observe(mockEmptyListObserver.testLifecycleOwner(), mockEmptyListObserver)
        deliveryListViewModel.deliveryResult.observe(mockDeliveryResultObserver.testLifecycleOwner(), mockDeliveryResultObserver)
        deliveryListViewModel.swipeContainer.observe(
            mockFetchObserver.testLifecycleOwner(),
            mockFetchObserver
        )
        deliveryListViewModel.noInternetOrError.observe(
            mockNoInternetObserver.testLifecycleOwner(),
            mockNoInternetObserver
        )
        deliveryListViewModel.offline.observe(mockOfflineDataObserver.testLifecycleOwner(), mockOfflineDataObserver)


        FieldSetter.setField(
            deliveryListViewModel, deliveryListViewModel.javaClass.getDeclaredField("uiScope"),
            CoroutineScope(Dispatchers.Main)
        )

        FieldSetter.setField(
            deliveryListViewModel, deliveryListViewModel.javaClass.getDeclaredField("lastLoadedPage"),
            1
        )


    }

    @Test
    fun initiateDeliveryListFetchTest() {


        testRule()

        given { mockConnectionHandler.isConnected }.willReturn(true)

        verify(mockFetchObserver).onChanged(true)


    }

    @Test
    fun offlineDataMessageTest_whenNotConnected()
    {
        given { mockConnectionHandler.isConnected }.willReturn(false)

        deliveryListViewModel.checkInternetConnection()
        verify(mockOfflineDataObserver).onChanged(true)
    }

    @Test
    fun offlineDataMessageTest_whenConnected()
    {
        given { mockConnectionHandler.isConnected }.willReturn(true)

        deliveryListViewModel.checkInternetConnection()
        verify(mockOfflineDataObserver).onChanged(false)
    }

    @Test
    fun onScrolledToBottomTest_shouldInitiateFetch()
    {

        testRule()
        deliveryListViewModel.onScrolledToBottom(5,6,10, false)

        verify(mockFetchObserver, times(2)).onChanged(true)


    }


    @Test
    fun onScrolledToBottomTest_shouldNotInitiateFetch()
    {
        testRule()
        runBlocking {
            deliveryListViewModel.onScrolledToBottom(1,5,20, false)
            verify(mockFetchObserver).onChanged(true)
        }
    }


    @Test
    fun onScrolledToBottomTest_shouldNotInitiateFetchWhenLoadingAlready()
    {
        testRule()
        deliveryListViewModel.onScrolledToBottom(1,5,20, true)

        verify(mockFetchObserver).onChanged(true)

    }



    private fun testRule()
    {
        var deliveryResponse: List<Delivery> = mock()
        given { runBlocking { mockRepository.getDeliveries(0) } }.willReturn(Either.Success(DeliveryResponse(1,deliveryResponse)))
        given { deliveryResponse.isNotEmpty() }.willReturn(true)
    }

}
package com.karthik.delivery.deliverylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karthik.delivery.base.network.ConnectionHandler
import com.karthik.delivery.base.util.Failure
import com.karthik.delivery.deliverylist.data.api.DeliveryApi
import com.karthik.delivery.deliverylist.data.repo.DeliveryRepository
import com.karthik.delivery.deliverylist.data.DeliveryResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * created by Karthik A on 09/03/19
 */
class DeliveryListViewModel(
    private val deliveryRepository: DeliveryRepository,
    private val connectionHandler: ConnectionHandler
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var lastLoadedPage = 0
    private var isLastPageReached = false

    //Delivery response data holder
    private val _deliveryResult = MutableLiveData<DeliveryResponse>()
    val deliveryResult: LiveData<DeliveryResponse>
        get() = _deliveryResult

    private val _swipeContainer = MutableLiveData<Boolean>()
    val swipeContainer: LiveData<Boolean>
        get() = _swipeContainer

    private val _noInternetOrError = MutableLiveData<Boolean>()
    val noInternetOrError: LiveData<Boolean>
        get() = _noInternetOrError

    private val _offline = MutableLiveData<Boolean>()
    val offline: LiveData<Boolean>
        get() = _offline

    private val _emptyListView = MutableLiveData<Boolean>()
    val emptyListView: LiveData<Boolean>
        get() = _emptyListView


    init {
        initiateDeliveryListFetch(page = 1)
    }


    fun onPullToRefresh()
    {
        initiateDeliveryListFetch(page = 1)
    }

    private fun initiateDeliveryListFetch(page: Int) {
        _swipeContainer.value = true
        getDeliveries(page)
    }

    private fun getDeliveries(page: Int) {
        uiScope.launch {

            val result = deliveryRepository.getDeliveries((page -1) * DeliveryRepository.ITEMS_PER_PAGE)

            result.either(::handleFetchDeliveriesFailure, ::handleFetchDeliveriesSuccess)

            _swipeContainer.value = false
        }

    }


    private fun handleFetchDeliveriesSuccess(deliveries: DeliveryResponse) {


        lastLoadedPage = deliveries.page

        if(deliveries.delivery!!.isEmpty())
        {
            isLastPageReached = true

            if(deliveries.page == 1)
            {
                _emptyListView.value = true
            }
        }else
        {
            _emptyListView.value = false
            checkInternetConnection()
            _deliveryResult.value = deliveries
        }
    }

    fun checkInternetConnection() {

        if(lastLoadedPage == 1)
            _offline.value = !connectionHandler.isConnected
    }

    private fun handleFetchDeliveriesFailure(failure: Failure) {

        if (lastLoadedPage == 0)
            _noInternetOrError.value = true
    }


    fun onScrolledToBottom(
        visibleItemCount: Int,
        lastVisibleItemPosition: Int,
        totalItemCount: Int,
        loadingAlready: Boolean
    ) {

        if (!isLastPageReached && !loadingAlready && visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            initiateDeliveryListFetch(lastLoadedPage + 1)
        }
    }


    fun retryClicked() {

        _noInternetOrError.value = false
        initiateDeliveryListFetch(page = 1)

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }


}
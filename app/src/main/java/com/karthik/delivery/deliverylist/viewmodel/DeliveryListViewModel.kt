package com.karthik.delivery.deliverylist.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.karthik.delivery.base.network.ConnectionHandler
import com.karthik.delivery.base.util.Failure
import com.karthik.delivery.deliverylist.data.Delivery
import com.karthik.delivery.deliverylist.data.DeliveryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * created by Karthik A on 09/03/19
 */
class DeliveryListViewModel(private val deliveryRepository: DeliveryRepository, private val connectionHandler: ConnectionHandler) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var lastLoadedPage = 0
    private var isLastPageReached = false

    private val offset: Int
        get() {
            return lastLoadedPage * 20
        }

    //Delivery response data holder
    private val _deliveryResult = MutableLiveData<List<Delivery>>()
    val deliveryResult: LiveData<List<Delivery>>
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
        initiateDeliveryListFetch()
    }

    private fun initiateDeliveryListFetch() {
        _swipeContainer.value = true
        getDeliveries()
    }

    private fun getDeliveries() {
        uiScope.launch {

            val result = deliveryRepository.getDeliveries(offset)

            result.either(::handleFetchDeliveriesFailure, ::handleFetchDeliveriesSuccess)

            _swipeContainer.value = false
        }

    }


    private fun handleFetchDeliveriesSuccess(deliveries: List<Delivery>) {

        // when result is not empty, last loaded page is incremented to move to next page
        if (deliveries.isNotEmpty()) {
            lastLoadedPage++
            _emptyListView.value = false
            checkInternetConnection()
            _deliveryResult.value = deliveries

        } else {

            if(lastLoadedPage == 0)
            {
                _emptyListView.value = true

            }else{
                _emptyListView.value = false
                isLastPageReached = true
            }
        }
    }

    fun checkInternetConnection() {

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
            initiateDeliveryListFetch()
        }
    }


    fun retryClicked() {

        _noInternetOrError.value = false
        initiateDeliveryListFetch()

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }


}
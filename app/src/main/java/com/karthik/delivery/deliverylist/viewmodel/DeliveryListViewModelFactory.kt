package com.karthik.delivery.deliverylist.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.karthik.delivery.base.network.ConnectionHandler
import com.karthik.delivery.deliverylist.data.DeliveryRepository
import javax.inject.Inject


/**
 * created by Karthik A on 09/03/19
 */
class DeliveryListViewModelFactory
@Inject constructor(
    private val deliveryRepository: DeliveryRepository,
    private val connectionHandler: ConnectionHandler
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeliveryListViewModel(deliveryRepository, connectionHandler) as T
    }

}
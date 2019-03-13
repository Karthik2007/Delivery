package com.karthik.delivery.deliverylist.di

import com.karthik.delivery.base.di.AppComponent
import com.karthik.delivery.deliverylist.view.DeliveryListActivity
import dagger.Component

/**
 * created by Karthik A on 11/03/19
 */
@DeliveryScope
@Component(dependencies = [AppComponent::class], modules = [DeliveryModule::class])
interface DeliveryComponent {
    fun injectDependencies(target: DeliveryListActivity)
}
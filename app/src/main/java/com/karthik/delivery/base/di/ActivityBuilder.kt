package com.karthik.delivery.base.di

import com.karthik.delivery.deliverylist.di.DeliveryModule
import com.karthik.delivery.deliverylist.view.DeliveryListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * created by Karthik A on 2019-05-25
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [DeliveryModule::class])
    abstract fun bindDeliveryListActivity(): DeliveryListActivity

}
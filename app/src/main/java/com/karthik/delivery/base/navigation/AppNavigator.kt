package com.karthik.delivery.base.navigation

import android.content.Context
import android.content.Intent
import com.karthik.delivery.deliverylist.data.Delivery
import com.karthik.delivery.deliverymap.view.DeliveryMapActivity


/**
 *
 * App level navigator to consolidate all the routes in the application.
 * If we use Navigation Component , this can be obsolete
 *
 * created by Karthik A on 09/03/19
 */
class AppNavigator {


    fun goToDeliveryMapActivity(context: Context, data: Delivery)
    {
        var intent = Intent(context, DeliveryMapActivity::class.java)
        intent.putExtra("delivery",data)

        context.startActivity(intent)
    }


}
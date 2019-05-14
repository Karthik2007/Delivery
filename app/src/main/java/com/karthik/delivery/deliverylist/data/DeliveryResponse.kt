package com.karthik.delivery.deliverylist.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


/**
 * created by Karthik A on 09/03/19
 */
@Parcelize
@Entity
data class Delivery(
    @PrimaryKey val id: Int,
    val description: String,
    val imageUrl: String,
    @Embedded val location: Location
) : Parcelable

@Parcelize
data class Location(
    val lat: Double,
    val lng: Double,
    val address: String
) : Parcelable

@Parcelize
data class DeliveryResponse(var page: Int, var delivery: List<Delivery>?): Parcelable
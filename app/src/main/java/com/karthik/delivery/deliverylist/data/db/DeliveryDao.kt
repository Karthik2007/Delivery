package com.karthik.delivery.deliverylist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karthik.delivery.deliverylist.data.Delivery


/**
 * created by Karthik A on 12/05/19
 */
@Dao
interface DeliveryDao {

    @Query("Select * from delivery ORDER BY id asc LIMIT :limit  OFFSET :offset ")
    fun get(offset: Int, limit: Int): List<Delivery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(deliveries: List<Delivery>?): Array<Long>
}
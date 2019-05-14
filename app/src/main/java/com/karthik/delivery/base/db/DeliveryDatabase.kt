package com.karthik.delivery.base.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karthik.delivery.deliverylist.data.Delivery
import com.karthik.delivery.deliverylist.data.db.DeliveryDao


/**
 * created by Karthik A on 12/05/19
 */
@Database(entities = [Delivery::class], version = 1)
abstract class DeliveryDatabase: RoomDatabase() {

    abstract fun deliveryDao(): DeliveryDao


    companion object {
        @Volatile
        private var INSTANCE: DeliveryDatabase? = null

        fun getDatabase(context: Context): DeliveryDatabase{

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DeliveryDatabase::class.java,
                    "delivery_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
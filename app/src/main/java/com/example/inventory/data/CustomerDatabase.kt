package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Customer::class], version = 1, exportSchema = false)
abstract class CustomerDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao

    companion object {
        @Volatile
        private var Instance: CustomerDatabase? = null

        fun getDatabase(context: Context): CustomerDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CustomerDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
package com.example.inventory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(customer: Customer)

    @Update
    suspend fun update(customer: Customer)

    @Delete
    suspend fun delete(customer: Customer)

    @Query("SELECT * from customer WHERE id = :id")
    fun getCustomer(id: Int): Flow<Customer>

    @Query("SELECT * from customer ORDER BY name ASC")
    fun getAllCustomers(): Flow<List<Customer>>

    @Query("SELECT * from customer WHERE name LIKE :search")
    suspend fun getSearchedCustomers(search: String?): List<Customer>
}
/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

class OfflineCustomersRepository(private val customerDao: CustomerDao) : CustomersRepository {

    override fun getAllCustomersStream(): Flow<List<Customer>> = customerDao.getAllCustomers()

    override fun getCustomerStream(id: Int): Flow<Customer?> = customerDao.getCustomer(id)

    override suspend fun insertCustomer(customer: Customer) = customerDao.insert(customer)

    override suspend fun deleteCustomer(customer: Customer) = customerDao.delete(customer)

    override suspend fun updateCustomer(customer: Customer) = customerDao.update(customer)

    override suspend fun getSearchedCustomers(search: String) = customerDao.getSearchedCustomers(search)
}

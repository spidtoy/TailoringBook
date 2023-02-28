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

package com.example.inventory.ui.customer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.CustomersRepository

/**
 * View Model to validate and insert items in the Room database.
 */
class CustomerEntryViewModel(private val customersRepository: CustomersRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var customerUiState by mutableStateOf(CustomerUiState())
        private set

    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(newCustomerUiState: CustomerUiState) {
        customerUiState = newCustomerUiState.copy( actionEnabled = newCustomerUiState.isValid())
    }

    suspend fun saveCustomer() {
        if (customerUiState.isValid()) {
            customersRepository.insertCustomer(customerUiState.toCustomer())
        }
    }
}

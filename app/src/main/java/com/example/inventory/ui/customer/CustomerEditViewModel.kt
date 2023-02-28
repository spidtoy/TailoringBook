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
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.CustomersRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve and update an item from the [ItemsRepository]'s data source.
 */
class CustomerEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val customersRepository: CustomersRepository
) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var customerUiState by mutableStateOf(CustomerUiState())
        private set

    private val customerId: Int = checkNotNull(savedStateHandle[CustomerEditDestination.customerIdArg])

    init {
        viewModelScope.launch {
            customerUiState = customersRepository.getCustomerStream(customerId)
                .filterNotNull()
                .first()
                .toCustomerUiState(actionEnabled = true)
        }
    }

    fun updateUiState(newItemUiState: CustomerUiState) {
        customerUiState = newItemUiState.copy( actionEnabled = newItemUiState.isValid())
    }

    suspend fun updateCustomer() {
        if (customerUiState.isValid()) {
            customersRepository.updateCustomer(customerUiState.toCustomer())
        }
    }

}

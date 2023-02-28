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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.CustomersRepository
import kotlinx.coroutines.flow.*

/**
 * ViewModel to retrieve, update and delete an item from the data source.
 */
class CustomerDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val customersRepository: CustomersRepository
) : ViewModel() {

    private val customerId: Int = checkNotNull(savedStateHandle[CustomerDetailsDestination.customerIdArg])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val uiState: StateFlow<CustomerUiState> =
        customersRepository.getCustomerStream(customerId)
            .filterNotNull()
            .map {
                it.toCustomerUiState()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CustomerUiState()
            )

    suspend fun deleteCustomer() {
        customersRepository.deleteCustomer(uiState.value.toCustomer())
    }
}

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

package com.example.inventory.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Customer
import com.example.inventory.data.CustomersRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * View Model to retrieve all items in the Room database.
 */
class HomeViewModel(val customersRepository: CustomersRepository) : ViewModel() {
    var search by mutableStateOf("")
    private set

    var list: List<Customer> by mutableStateOf(listOf())
        private set

    var searchQuery = ""

    val homeUiState: StateFlow<HomeUiState> = customersRepository.getAllCustomersStream()
        .map { HomeUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    fun searchedList() = viewModelScope.launch {
          list = customersRepository.getSearchedCustomers("%$searchQuery%")
    }


    fun updateSearch (newText: String) {
        search = newText
        searchQuery = newText
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val customerList: List<Customer> = listOf())

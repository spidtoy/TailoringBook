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

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Customer
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.MyComposables.ModifiedMyTextField
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for Home screen
 */
@Composable
fun HomeScreen(
    navigateToCustomerEntry: () -> Unit,
    navigateToCustomerUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val search = viewModel.search
    val searchList = viewModel.list

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToCustomerEntry,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.customer_entry_title),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
    ) { innerPadding ->
        Column(modifier= modifier) {
            Surface(shape = RoundedCornerShape(50.dp), elevation = 6.dp,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp) )
            {
                ModifiedMyTextField(search = search, onValueChange = { text ->
                    viewModel.updateSearch(text)
                    viewModel.searchedList()
                }, onCleared = {viewModel.updateSearch("")})
            }
            if (viewModel.search != "") {
                SearchList(customerList = searchList,
                onCustomerClick = { navigateToCustomerUpdate(it.id)})
            } else {
                HomeBody(
                    customerList = homeUiState.customerList,
                    onCustomerClick = navigateToCustomerUpdate,
                    modifier = Modifier.padding(innerPadding)
                )
            }

        }
    }
}


@Composable
private fun HomeBody(
    customerList: List<Customer>,
    onCustomerClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InventoryListHeader()
        Divider()
        if (customerList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_customer_description),
                style = MaterialTheme.typography.subtitle2
            )
        } else {
            InventoryList(customerList = customerList, onCustomerClick = { onCustomerClick(it.id) })
        }
    }
}

@Composable
private fun InventoryList(
    customerList: List<Customer>,
    onCustomerClick: (Customer) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items = customerList, key = { it.id }) { item ->
            InventoryItem(customer = item, onItemClick = onCustomerClick)
            Divider()
        }
    }
}

@Composable
private fun SearchList(
    customerList: List<Customer>,
    onCustomerClick: (Customer) -> Unit,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(50.dp),
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items = customerList, key = { it.id }) { item ->
            SearchItem(customer = item, onItemClick = onCustomerClick)
        }
    }
}

@Composable
private fun InventoryListHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        headerList.forEach {
            Text(
                text = stringResource(it.headerStringId),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
private fun InventoryItem(
    customer: Customer,
    onItemClick: (Customer) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onItemClick(customer) }
        .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = customer.name,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = customer.phone.toString().padStart(11, '0'),
        )

    }
}

@Composable
private fun SearchItem(
    customer: Customer,
    onItemClick: (Customer) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onItemClick(customer) }
        .padding(start = 2.dp, end = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = customer.name,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = customer.phone.toString().padStart(11, '0'),
        )

    }
}

private data class InventoryHeader(@StringRes val headerStringId: Int)

private val headerList = listOf(
    InventoryHeader(headerStringId = R.string.name),
    InventoryHeader(headerStringId = R.string.phone),
)

@Preview(showBackground = true)
@Composable
fun HomeScreenRoutePreview() {
    InventoryTheme {
        HomeBody(
            listOf(
            ),
            onCustomerClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryListHeaderPreview() {

        InventoryListHeader()


}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    InventoryTheme {
        HomeScreen(
            navigateToCustomerEntry = { },
        navigateToCustomerUpdate = { },
                )
    }
}
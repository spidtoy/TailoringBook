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

package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.customer.*
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.home.HomeScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToCustomerEntry = { navController.navigate(CustomerEntryDestination.route) },
                navigateToCustomerUpdate = {
                    navController.navigate("${CustomerDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = CustomerEntryDestination.route) {
            CustomerEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = CustomerDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(CustomerDetailsDestination.customerIdArg) {
                type = NavType.IntType
            })
        ) {
            CustomerDetailsScreen(
                navigateToEditCustomer = { navController.navigate("${CustomerEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = CustomerEditDestination.routeWithArgs,
            arguments = listOf(navArgument(CustomerEditDestination.customerIdArg) {
                type = NavType.IntType
            })
        ) {
            CustomerEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

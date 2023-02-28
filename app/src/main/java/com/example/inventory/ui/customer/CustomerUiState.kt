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

import com.example.inventory.data.Customer


/**
 * Represents Ui State for an Item.
 */
data class CustomerUiState(
    val id: Int = 0,
    val name: String = "",
    val phone: String = "",
    val price: String = "",
    val deposit: String = "",
    val balance: String = "",
    val address: String = "",
    val shoulder: String = "",
    val bust: String = "",
    val underBust: String = "",
    val roundUnderBust: String = "",
    val armhole: String = "",
    val halfLength: String = "",
    val sleeveLength: String = "",
    val roundSleeve: String = "",
    val hip: String = "",
    val hipLine: String = "",
    val kneeLength: String = "",
    val neckDepth: String = "",
    val neckWidth: String = "",
    val nippleToNipple: String = "",
    val shoulderToNipple: String = "",
    val slimWaist: String = "",
    val topLength: String = "",
    val skirtLength: String = "",
    val trouserLength: String = "",
    val shortLength: String = "",
    val longLength: String = "",
    val flap: String = "",
    val actionEnabled: Boolean = false
)

/**
 * Extension function to convert [ItemUiState] to [Item]. If the value of [ItemUiState.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemUiState] is not a valid [Int], then the quantity will be set to 0
 */
fun CustomerUiState.toCustomer(): Customer = Customer(
    id = id,
    name = name,
    phone = phone.toLongOrNull() ?: 0,
    price = price.toDoubleOrNull() ?: 0.00,
    deposit = deposit.toDoubleOrNull() ?: 0.00,
    balance = balance.toDoubleOrNull() ?: 0.00,
    address = address,
    shoulder = shoulder.toDoubleOrNull() ?: 0.0,
    bust = bust.toDoubleOrNull() ?: 0.0,
    underBust = underBust.toDoubleOrNull() ?: 0.0,
    roundUnderBust = roundUnderBust.toDoubleOrNull() ?: 0.0,
    armhole = armhole.toDoubleOrNull() ?: 0.0,
    halfLength = halfLength.toDoubleOrNull() ?: 0.0,
    sleeveLength = sleeveLength.toDoubleOrNull() ?: 0.0,
    roundSleeve = roundSleeve.toDoubleOrNull() ?: 0.0,
    hip = hip.toDoubleOrNull() ?: 0.0,
    hipLine = hipLine.toDoubleOrNull() ?: 0.0,
    kneeLength = kneeLength.toDoubleOrNull() ?: 0.0,
    neckDepth = neckDepth.toDoubleOrNull() ?: 0.0,
    neckWidth = neckWidth.toDoubleOrNull() ?: 0.0,
    nippleToNipple = nippleToNipple.toDoubleOrNull() ?: 0.0,
    shoulderToNipple = shoulderToNipple.toDoubleOrNull() ?: 0.0,
    slimWaist = slimWaist.toDoubleOrNull() ?: 0.0,
    topLength = topLength.toDoubleOrNull() ?: 0.0,
    skirtLength = skirtLength.toDoubleOrNull() ?: 0.0,
    trouserLength = trouserLength.toDoubleOrNull() ?: 0.0,
    shortLength = shortLength.toDoubleOrNull() ?: 0.0,
    longLength = longLength.toDoubleOrNull() ?: 0.0,
    flap = flap.toDoubleOrNull() ?: 0.0,
)

/**
 * Extension function to convert [Item] to [ItemUiState]
 */
fun Customer.toCustomerUiState(actionEnabled: Boolean = false): CustomerUiState = CustomerUiState(
    id = id,
    name = name,
    phone = phone.toString().padStart(11, '0'),
    price = "%.2f".format(price),
    deposit = "%.2f".format(deposit),
    balance = "%.2f".format(balance),
    address = address,
    shoulder = "%.2f".format(shoulder),
    bust = "%.2f".format(bust),
    underBust = "%.2f".format(underBust),
    roundUnderBust = "%.2f".format(roundUnderBust),
    armhole = "%.2f".format(armhole),
    halfLength = "%.2f".format(halfLength),
    sleeveLength = "%.2f".format(sleeveLength),
    roundSleeve = "%.2f".format(roundSleeve),
    hip = "%.2f".format(hip),
    hipLine = "%.2f".format(hipLine),
    kneeLength = "%.2f".format(kneeLength),
    neckDepth = "%.2f".format(neckDepth),
    neckWidth = "%.2f".format(neckWidth),
    nippleToNipple = "%.2f".format(nippleToNipple),
    shoulderToNipple = "%.2f".format(shoulderToNipple),
    slimWaist = "%.2f".format(slimWaist),
    topLength = "%.2f".format(topLength),
    skirtLength = "%.2f".format(skirtLength),
    trouserLength = "%.2f".format(trouserLength),
    shortLength = "%.2f".format(shortLength),
    longLength = "%.2f".format(longLength),
    flap = "%.2f".format(flap),
    actionEnabled = actionEnabled
)

fun CustomerUiState.isValid() : Boolean {
    return name.isNotBlank()
 }


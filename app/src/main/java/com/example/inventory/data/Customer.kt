/*
 * Copyright (C) 2022 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phone: Long,
    val price: Double = 0.0,
    val deposit: Double = 0.0,
    val balance: Double = 0.0,
    val address: String =" ",
    val shoulder: Double = 0.0,
    val bust: Double = 0.0,
    val underBust: Double = 0.0,
    val roundUnderBust: Double = 0.0,
    val armhole: Double = 0.0,
    val halfLength: Double = 0.0,
    val sleeveLength: Double = 0.0,
    val roundSleeve: Double = 0.0,
    val hip: Double = 0.0,
    val hipLine: Double = 0.0,
    val kneeLength: Double = 0.0,
    val neckDepth: Double = 0.0,
    val neckWidth: Double = 0.0,
    val nippleToNipple: Double = 0.0,
    val shoulderToNipple: Double = 0.0,
    val slimWaist: Double = 0.0,
    val topLength: Double = 0.0,
    val skirtLength: Double = 0.0,
    val trouserLength: Double = 0.0,
    val shortLength: Double = 0.0,
    val longLength: Double = 0.0,
    val flap: Double = 0.0
)

package com.crystal2033.qrextractor.scanner_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val username: String,
    val password: String,

    @PrimaryKey(autoGenerate = true)
    val userId: Long
)
package com.crystal2033.qrextractor.core.remote_server.data.dto

import android.graphics.Bitmap

interface InventarizedDTO<M> {
    val id: Long
    val image: String
    val inventoryNumber: String
    val name: String
    val cabinetId: Long
    fun toModel(): M
}
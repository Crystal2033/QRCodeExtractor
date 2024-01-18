package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap

interface InventarizedModel<D> {
    val id: Long
    val image: Bitmap?
    val inventoryNumber: String
    val name: String
    val cabinetId: Long
    fun toDTO(): D
}
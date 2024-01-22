package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap

interface InventarizedModel<D> {
    var id: Long
    var image: Bitmap?
    var inventoryNumber: String
    var name: String
    var cabinetId: Long
    fun toDTO(): D
}
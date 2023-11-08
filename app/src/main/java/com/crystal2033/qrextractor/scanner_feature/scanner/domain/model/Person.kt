package com.crystal2033.qrextractor.scanner_feature.scanner.domain.model

import androidx.compose.ui.graphics.ImageBitmap

data class Person (
    val id: Long,
    val department: Department,
    val firstName: String,
    val image: ImageBitmap,
    val secondName: String,
    val title: Title,
    val workSpace: WorkSpace
): QRScannableData {
    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.PERSON
    }

    override fun getDatabaseID(): Long {
        return id
    }

}

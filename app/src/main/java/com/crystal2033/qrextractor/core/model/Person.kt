package com.crystal2033.qrextractor.core.model

import androidx.compose.ui.graphics.ImageBitmap
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class Person(
    val id: Long = 0,
    val department: Department? = null,
    val firstName: String = "",
    val image: ImageBitmap? = null,
    val secondName: String = "",
    val title: Title? = null,
    val workSpace: WorkSpace? = null
) : QRScannableData {


    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.PERSON
    }

    override fun getDatabaseID(): Long {
        return id
    }

//    fun toPersonDto() : PersonDto{
//        return PersonDto()
//    }
}

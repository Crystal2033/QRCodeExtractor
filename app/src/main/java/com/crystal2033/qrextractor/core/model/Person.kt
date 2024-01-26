//package com.crystal2033.qrextractor.core.model
//
//import android.graphics.Bitmap
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.graphics.asAndroidBitmap
//import com.crystal2033.qrextractor.core.dto.PersonDto
//import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters
//import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
//
//data class Person(
//    val id: Long = 0,
//    val department: Department? = null,
//    val firstName: String = "",
//    val image: Bitmap? = null,
//    val secondName: String = "",
//    val title: Title? = null,
//    val workSpace: WorkSpace? = null
//) : QRScannableData {
//
//
//    override fun getDatabaseTableName(): DatabaseObjectTypes {
//        return DatabaseObjectTypes.PERSON
//    }
//
//    override fun getDatabaseID(): Long {
//        return id
//    }
//
//    fun toPersonDto(): PersonDto {
//        return PersonDto(
//            department = department!!.toDepartmentDto(),
//            firstName = firstName,
//            secondName = secondName,
//            id = id,
//            image = image?.let { StaticConverters.fromBitmapToString(image) } ?: "" ,
//            jsondataForQR = "",
//            title = title!!.toTitleDto(),
//            workSpace = workSpace!!.toWorkspaceDto()
//        )
//    }
//}

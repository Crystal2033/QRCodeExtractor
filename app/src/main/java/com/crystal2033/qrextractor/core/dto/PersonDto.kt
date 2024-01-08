package com.crystal2033.qrextractor.core.dto

import androidx.compose.ui.graphics.asAndroidBitmap
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters
import com.crystal2033.qrextractor.core.model.Person


data class PersonDto(
    val department: DepartmentDto,
    val firstName: String,
    val id: Long,
    val image: String,
    val jsondataForQR: String,
    val secondName: String,
    val title: TitleDto,
    val workSpace: WorkSpaceDto
) {
    fun toPerson(): Person {
        return Person(
            id = id,
            firstName = firstName,
            secondName = secondName,
            image = StaticConverters.fromBytesIntoImageBitmap(image).asAndroidBitmap(),
            title = title.toTitle(),
            department = department.toDepartment(),
            workSpace = workSpace.toWorkSpace()
        )
    }
}



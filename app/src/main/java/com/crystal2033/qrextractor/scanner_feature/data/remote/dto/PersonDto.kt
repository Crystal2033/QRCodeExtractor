package com.crystal2033.qrextractor.scanner_feature.data.remote.dto

import android.content.Context
import com.crystal2033.qrextractor.scanner_feature.StaticConverters
import com.crystal2033.qrextractor.scanner_feature.domain.model.Person


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
            image = StaticConverters.fromBytesIntoImageBitmap(image),
            title = title.toTitle(),
            department = department.toDepartment(),
            workSpace = workSpace.toWorkSpace()
        )
    }
}



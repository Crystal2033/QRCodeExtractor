package com.crystal2033.qrextractor.scanner_feature.data.remote.dto

import com.crystal2033.qrextractor.scanner_feature.domain.model.Department

data class DepartmentDto(
    val id: Int,
    val name: String
) {
    fun toDepartment(): Department {
        return Department(
            id = id,
            name = name
        )
    }
}
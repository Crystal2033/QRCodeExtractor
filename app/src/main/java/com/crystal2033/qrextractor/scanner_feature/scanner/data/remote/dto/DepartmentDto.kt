package com.crystal2033.qrextractor.scanner_feature.scanner.data.remote.dto

import com.crystal2033.qrextractor.core.model.Department

data class DepartmentDto(
    val id: Long,
    val name: String
) {
    fun toDepartment(): Department {
        return Department(
            id = id,
            name = name
        )
    }
}
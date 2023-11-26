package com.crystal2033.qrextractor.core.dto

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
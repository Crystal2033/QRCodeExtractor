package com.crystal2033.qrextractor.core.model

import com.crystal2033.qrextractor.core.dto.DepartmentDto

data class Department(
    val name: String,
    val id: Long
)
{
    fun toDepartmentDto() : DepartmentDto{
        return DepartmentDto(
            id = id,
            name = name
        )
    }
}

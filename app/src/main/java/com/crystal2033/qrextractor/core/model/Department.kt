package com.crystal2033.qrextractor.core.model

import com.crystal2033.qrextractor.core.dto.DepartmentDto

data class Department(
    var name: String,
    var id: Long
)
{
    fun toDepartmentDto() : DepartmentDto{
        return DepartmentDto(
            id = id,
            name = name
        )
    }
}

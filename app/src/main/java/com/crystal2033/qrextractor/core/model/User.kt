package com.crystal2033.qrextractor.core.model

import com.crystal2033.qrextractor.core.localdb.entity.UserEntity

data class User(
    val id: Long,
    var firstName: String,
    var secondName: String,
    var login: String,
    var organizationId: Long
) {
    fun toUserEntity(): UserEntity {
        return UserEntity(
            login = login,
            firstName = firstName,
            secondName = secondName,
            organizationId = organizationId
        )
    }
}
package com.crystal2033.qrextractor.core.model

data class User(
    val id: Long,
    var firstName: String,
    var secondName: String,
    var login: String,
    var organizationId: Long
)
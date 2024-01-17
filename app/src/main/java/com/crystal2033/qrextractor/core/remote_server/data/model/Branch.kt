package com.crystal2033.qrextractor.core.remote_server.data.model

data class Branch(
    val id: Long,
    val name: String,
    val cityName: String,
    val organizationId: Long,
    val cityId: Long
) {
}
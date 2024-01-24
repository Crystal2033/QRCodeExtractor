package com.crystal2033.qrextractor.core.remote_server.data.model

data class Branch(
    val id: Long = 0L,
    val name: String = "",
    val cityName: String = "",
    val organizationId: Long = 0L,
    val cityId: Long = 0L
) {
}
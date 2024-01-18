package com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle

data class BundleID(
    val orgId: Long,
    val branchId: Long,
    val buildingId: Long,
    val cabinetId: Long,
    val deviceId: Long
) {
}
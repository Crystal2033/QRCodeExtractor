package com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces

import com.crystal2033.qrextractor.core.remote_server.data.model.Cabinet
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface CabinetRepository {
    fun getCabinetsByOrgBranchAndBuilding(
        buildingId: Long
    ): Flow<Resource<List<Cabinet>>>
}
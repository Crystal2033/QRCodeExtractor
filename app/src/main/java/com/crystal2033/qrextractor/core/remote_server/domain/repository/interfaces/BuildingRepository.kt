package com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces

import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface BuildingRepository {
    fun getBuildingsByOrgAndBranch(
        branchId: Long
    ): Flow<Resource<List<Building>>>

    fun getBuildingById(buildingId: Long): Flow<Resource<Building>>
}
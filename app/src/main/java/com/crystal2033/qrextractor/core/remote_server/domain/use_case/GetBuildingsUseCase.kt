package com.crystal2033.qrextractor.core.remote_server.domain.use_case

import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.BuildingRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetBuildingsUseCase(
    private val buildingRepository: BuildingRepository
) {
    operator fun invoke(branchId: Long): Flow<Resource<List<Building>>> {
        return buildingRepository.getBuildingsByOrgAndBranch(branchId)
    }
}
package com.crystal2033.qrextractor.core.remote_server.domain.use_case.building

import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.BuildingRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetBuildingUseCase(
    private val buildingRepository: BuildingRepository
) {
    operator fun invoke(buildingId: Long): Flow<Resource<Building>> {
        return buildingRepository.getBuildingById(buildingId)
    }
}
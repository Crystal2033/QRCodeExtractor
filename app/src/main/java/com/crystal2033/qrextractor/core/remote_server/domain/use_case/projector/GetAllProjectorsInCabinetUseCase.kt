package com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector

import com.crystal2033.qrextractor.core.remote_server.data.model.Projector
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ProjectorRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetAllProjectorsInCabinetUseCase(
    private val projectorRepository: ProjectorRepository
) {
    operator fun invoke(cabinetId: Long): Flow<Resource<List<Projector>>> {
        return projectorRepository.getAllDevicesInCabinet(cabinetId)
    }
}
package com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector

import com.crystal2033.qrextractor.core.remote_server.data.dto.ProjectorDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Projector
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ProjectorRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateProjectorUseCase(
    private val projectorRepository: ProjectorRepository
) {
    operator fun invoke(projectorDTO: ProjectorDTO): Flow<Resource<Projector>> {
        return projectorRepository.updateDevice(projectorDTO)
    }
}
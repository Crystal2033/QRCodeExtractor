package com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector

import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ProjectorRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.DeleteDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteProjectorUseCase(
    private val projectorRepository: ProjectorRepository
) : DeleteDeviceUseCaseInvoker {
    override operator fun invoke(deviceId: Long): Flow<Resource<Unit>> {
        return projectorRepository.deleteDevice(deviceId)
    }
}
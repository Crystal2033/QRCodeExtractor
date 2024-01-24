package com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ProjectorRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetProjectorUseCase(
    private val projectorRepository: ProjectorRepository
) : GetDeviceUseCaseInvoker{
    override operator fun invoke(deviceId: Long): Flow<Resource<InventarizedAndQRScannableModel>> {
        return projectorRepository.getDeviceById(deviceId)
    }
}
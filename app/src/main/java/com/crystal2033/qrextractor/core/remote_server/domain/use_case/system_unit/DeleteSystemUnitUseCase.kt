package com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit

import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.SystemUnitRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.DeleteDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteSystemUnitUseCase(
    private val systemUnitRepository: SystemUnitRepository
) : DeleteDeviceUseCaseInvoker {
    override operator fun invoke(deviceId: Long): Flow<Resource<Unit>> {
        return systemUnitRepository.deleteDevice(deviceId)
    }
}
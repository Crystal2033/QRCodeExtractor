package com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair

import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ChairRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.DeleteDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteChairUseCase(
    private val chairRepository: ChairRepository
) : DeleteDeviceUseCaseInvoker {
    override operator fun invoke(deviceId: Long): Flow<Resource<Unit>> {
        return chairRepository.deleteDevice(deviceId)
    }
}
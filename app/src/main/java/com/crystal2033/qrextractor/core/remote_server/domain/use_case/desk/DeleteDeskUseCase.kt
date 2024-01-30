package com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk

import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.DeskRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.DeleteDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteDeskUseCase(
    private val deskRepository: DeskRepository
) : DeleteDeviceUseCaseInvoker {
    override fun invoke(deviceId: Long): Flow<Resource<Unit>> {
        return deskRepository.deleteDevice(deviceId)
    }
}
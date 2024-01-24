package com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk

import com.crystal2033.qrextractor.core.remote_server.data.model.Desk
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.DeskRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetDeskUseCase(
    private val deskRepository: DeskRepository
) : GetDeviceUseCaseInvoker{
    override operator fun invoke(deviceId: Long): Flow<Resource<InventarizedAndQRScannableModel>> {
        return deskRepository.getDeviceById(deviceId)
    }
}
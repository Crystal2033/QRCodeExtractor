package com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.Monitor
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.MonitorRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetMonitorUseCase(
    private val monitorRepository: MonitorRepository
) : GetDeviceUseCaseInvoker {
    override operator fun invoke(deviceId: Long): Flow<Resource<InventarizedAndQRScannableModel>> {
        return monitorRepository.getDeviceById(deviceId)
    }
}
package com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor

import com.crystal2033.qrextractor.core.remote_server.data.dto.MonitorDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.Monitor
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.MonitorRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateMonitorUseCase(
    private val monitorRepository: MonitorRepository
) {
    operator fun invoke(monitorDTO: MonitorDTO): Flow<Resource<InventarizedAndQRScannableModel>> {
        return monitorRepository.updateDevice(monitorDTO)
    }
}
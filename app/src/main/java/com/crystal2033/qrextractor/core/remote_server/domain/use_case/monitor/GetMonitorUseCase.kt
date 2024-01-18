package com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor

import com.crystal2033.qrextractor.core.remote_server.data.model.Monitor
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.MonitorRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetMonitorUseCase(
    private val monitorRepository: MonitorRepository
) {
    operator fun invoke(monitorId: Long): Flow<Resource<Monitor>> {
        return monitorRepository.getDeviceById(monitorId)
    }
}
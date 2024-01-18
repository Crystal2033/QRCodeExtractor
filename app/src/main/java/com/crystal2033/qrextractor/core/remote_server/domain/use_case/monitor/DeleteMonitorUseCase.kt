package com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor

import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.MonitorRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteMonitorUseCase(
    private val monitorRepository: MonitorRepository
) {
    operator fun invoke(monitorId: Long): Flow<Resource<Unit>> {
        return monitorRepository.deleteDevice(monitorId)
    }
}
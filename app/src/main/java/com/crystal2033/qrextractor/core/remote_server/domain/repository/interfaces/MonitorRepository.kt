package com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces

import com.crystal2033.qrextractor.core.remote_server.api.MonitorAPI
import com.crystal2033.qrextractor.core.remote_server.data.dto.MonitorDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Monitor

interface MonitorRepository : CRUDDeviceOperationsRepository<MonitorDTO, MonitorAPI> {
}
package com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces

import com.crystal2033.qrextractor.core.remote_server.api.DeskAPI
import com.crystal2033.qrextractor.core.remote_server.data.dto.DeskDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Desk

interface DeskRepository : CRUDDeviceOperationsRepository<DeskDTO, DeskAPI> {
}
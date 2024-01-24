package com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces

import com.crystal2033.qrextractor.core.remote_server.api.SystemUnitAPI
import com.crystal2033.qrextractor.core.remote_server.data.dto.SystemUnitDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.SystemUnit

interface SystemUnitRepository :
    CRUDDeviceOperationsRepository<SystemUnitDTO, SystemUnitAPI> {
}
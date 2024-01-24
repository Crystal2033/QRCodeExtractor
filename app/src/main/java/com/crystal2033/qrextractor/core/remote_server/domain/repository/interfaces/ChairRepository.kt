package com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces

import com.crystal2033.qrextractor.core.remote_server.api.ChairAPI
import com.crystal2033.qrextractor.core.remote_server.data.dto.ChairDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Chair

interface ChairRepository : CRUDDeviceOperationsRepository<ChairDTO, ChairAPI> {

}
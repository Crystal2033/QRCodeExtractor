package com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit

import com.crystal2033.qrextractor.core.remote_server.data.dto.SystemUnitDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.SystemUnit
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.SystemUnitRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateSystemUnitUseCase(
    private val systemUnitRepository: SystemUnitRepository
) {
    operator fun invoke(systemUnitDTO: SystemUnitDTO): Flow<Resource<SystemUnit>> {
        return systemUnitRepository.updateDevice(systemUnitDTO)
    }
}
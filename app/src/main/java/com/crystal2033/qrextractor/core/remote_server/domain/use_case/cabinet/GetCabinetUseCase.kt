package com.crystal2033.qrextractor.core.remote_server.domain.use_case.cabinet

import com.crystal2033.qrextractor.core.remote_server.data.model.Cabinet
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.CabinetRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCabinetUseCase(
    private val cabinetRepository: CabinetRepository
) {
    operator fun invoke(cabinetId: Long): Flow<Resource<Cabinet>> {
        return cabinetRepository.getCabinetById(cabinetId)
    }
}
package com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair

import com.crystal2033.qrextractor.core.remote_server.data.dto.ChairDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Chair
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ChairRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateChairUseCase (
    private val chairRepository: ChairRepository
) {
    operator fun invoke(chairDTO: ChairDTO): Flow<Resource<Chair>> {
        return chairRepository.updateDevice(chairDTO)
    }
}
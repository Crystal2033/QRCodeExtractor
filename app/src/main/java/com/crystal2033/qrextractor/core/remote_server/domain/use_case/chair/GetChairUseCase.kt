package com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair

import com.crystal2033.qrextractor.core.remote_server.data.model.Chair
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ChairRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetChairUseCase(
    private val chairRepository: ChairRepository
) {
    operator fun invoke(chairId: Long): Flow<Resource<Chair>> {
        return chairRepository.getDeviceById(chairId)
    }
}
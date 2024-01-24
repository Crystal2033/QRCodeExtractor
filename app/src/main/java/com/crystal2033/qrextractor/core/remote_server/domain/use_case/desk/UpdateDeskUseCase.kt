package com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk

import com.crystal2033.qrextractor.core.remote_server.data.dto.DeskDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Desk
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.DeskRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateDeskUseCase(
    private val deskRepository: DeskRepository
) {
    operator fun invoke(deskDTO: DeskDTO): Flow<Resource<InventarizedAndQRScannableModel>> {
        return deskRepository.updateDevice(deskDTO)
    }
}
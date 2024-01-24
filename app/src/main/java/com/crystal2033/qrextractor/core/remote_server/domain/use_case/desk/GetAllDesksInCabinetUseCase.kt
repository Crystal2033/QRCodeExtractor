package com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk

import com.crystal2033.qrextractor.core.remote_server.data.model.Desk
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.DeskRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetAllDesksInCabinetUseCase(
    private val deskRepository: DeskRepository
) {
    operator fun invoke(cabinetId: Long): Flow<Resource<List<InventarizedAndQRScannableModel>>> {
        return deskRepository.getAllDevicesInCabinet(cabinetId)
    }
}
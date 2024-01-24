package com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair

import com.crystal2033.qrextractor.core.remote_server.data.model.Chair
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ChairRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetAllChairsInCabinetUseCase(
    private val chairRepository: ChairRepository
) {
    operator fun invoke(cabinetId: Long) : Flow<Resource<List<InventarizedAndQRScannableModel>>>{
        return chairRepository.getAllDevicesInCabinet(cabinetId)
    }
}
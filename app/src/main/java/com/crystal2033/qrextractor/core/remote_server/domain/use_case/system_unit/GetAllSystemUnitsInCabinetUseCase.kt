package com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.SystemUnit
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.SystemUnitRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetAllSystemUnitsInCabinetUseCase(
    private val systemUnitRepository: SystemUnitRepository
) {
    operator fun invoke(cabinetId: Long): Flow<Resource<List<InventarizedAndQRScannableModel>>> {
        return systemUnitRepository.getAllDevicesInCabinet(cabinetId)
    }
}
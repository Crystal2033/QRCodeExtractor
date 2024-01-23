package com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard

import com.crystal2033.qrextractor.core.remote_server.data.model.Keyboard
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.KeyboardRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetAllKeyboardsInCabinetUseCase(
    private val keyboardRepository: KeyboardRepository
) {
    operator fun invoke(cabinetId: Long): Flow<Resource<List<Keyboard>>> {
        return keyboardRepository.getAllDevicesInCabinet(cabinetId)
    }
}
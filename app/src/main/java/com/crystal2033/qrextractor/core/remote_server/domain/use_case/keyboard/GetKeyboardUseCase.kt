package com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard

import com.crystal2033.qrextractor.core.remote_server.data.model.Keyboard
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.KeyboardRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetKeyboardUseCase(
    private val keyboardRepository: KeyboardRepository
) {
    operator fun invoke(keyboardId: Long): Flow<Resource<Keyboard>> {
        return keyboardRepository.getDeviceById(keyboardId)
    }
}
package com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard

import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.KeyboardRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteKeyboardUseCase(
    private val keyboardRepository: KeyboardRepository
) {
    operator fun invoke(keyboardId: Long): Flow<Resource<Unit>> {
        return keyboardRepository.deleteDevice(keyboardId)
    }
}
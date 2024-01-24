package com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard

import com.crystal2033.qrextractor.core.remote_server.data.dto.KeyboardDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.Keyboard
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.KeyboardRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateKeyboardUseCase(
    private val keyboardRepository: KeyboardRepository
) {
    operator fun invoke(keyboardDTO: KeyboardDTO): Flow<Resource<InventarizedAndQRScannableModel>> {
        return keyboardRepository.updateDevice(keyboardDTO)
    }
}
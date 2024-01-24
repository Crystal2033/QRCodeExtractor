package com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.Keyboard
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.KeyboardRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetKeyboardUseCase(
    private val keyboardRepository: KeyboardRepository
) : GetDeviceUseCaseInvoker {
    override operator fun invoke(deviceId: Long): Flow<Resource<InventarizedAndQRScannableModel>> {
        return keyboardRepository.getDeviceById(deviceId)
    }
}
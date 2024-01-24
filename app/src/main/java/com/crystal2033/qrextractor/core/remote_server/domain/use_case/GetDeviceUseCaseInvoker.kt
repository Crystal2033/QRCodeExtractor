package com.crystal2033.qrextractor.core.remote_server.domain.use_case

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetDeviceUseCaseInvoker {
    operator fun invoke(deviceId: Long): Flow<Resource<InventarizedAndQRScannableModel>>
}
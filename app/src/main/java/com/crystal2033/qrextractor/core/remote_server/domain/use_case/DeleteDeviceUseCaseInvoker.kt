package com.crystal2033.qrextractor.core.remote_server.domain.use_case

import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface DeleteDeviceUseCaseInvoker {
    operator fun invoke(deviceId: Long): Flow<Resource<Unit>>
}
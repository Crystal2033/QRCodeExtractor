package com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces

import com.crystal2033.qrextractor.core.remote_server.api.ChairAPI
import com.crystal2033.qrextractor.core.remote_server.api.KeyboardAPI
import com.crystal2033.qrextractor.core.remote_server.data.dto.ChairDTO
import com.crystal2033.qrextractor.core.remote_server.data.dto.KeyboardDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Chair
import com.crystal2033.qrextractor.core.remote_server.data.model.Keyboard

interface KeyboardRepository : CRUDDeviceOperationsRepository<Keyboard, KeyboardDTO, KeyboardAPI> {

}
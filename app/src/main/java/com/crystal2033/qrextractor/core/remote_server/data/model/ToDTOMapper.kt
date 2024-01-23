package com.crystal2033.qrextractor.core.remote_server.data.model

interface ToDTOMapper<D> {
    fun toDTO(): D
}
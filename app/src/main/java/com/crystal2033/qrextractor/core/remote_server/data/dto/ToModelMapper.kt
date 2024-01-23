package com.crystal2033.qrextractor.core.remote_server.data.dto

interface ToModelMapper<M> {
    fun toModel(): M
}
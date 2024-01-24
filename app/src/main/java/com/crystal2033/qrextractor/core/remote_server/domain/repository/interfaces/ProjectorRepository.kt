package com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces

import com.crystal2033.qrextractor.core.remote_server.api.DeskAPI
import com.crystal2033.qrextractor.core.remote_server.api.ProjectorAPI
import com.crystal2033.qrextractor.core.remote_server.data.dto.DeskDTO
import com.crystal2033.qrextractor.core.remote_server.data.dto.ProjectorDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Desk
import com.crystal2033.qrextractor.core.remote_server.data.model.Projector

interface ProjectorRepository : CRUDDeviceOperationsRepository<ProjectorDTO, ProjectorAPI> {
}
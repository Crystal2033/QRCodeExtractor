package com.crystal2033.qrextractor.core.remote_server.domain.use_case

import com.crystal2033.qrextractor.core.remote_server.data.model.Organization
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.OrganizationRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetOrganizationsUseCase(
    private val organizationRepository: OrganizationRepository
) {
    operator fun invoke(): Flow<Resource<List<Organization>>> {
        return organizationRepository.getOrganizations()
    }
}
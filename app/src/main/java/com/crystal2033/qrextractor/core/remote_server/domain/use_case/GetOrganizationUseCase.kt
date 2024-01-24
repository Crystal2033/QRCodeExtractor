package com.crystal2033.qrextractor.core.remote_server.domain.use_case

import com.crystal2033.qrextractor.core.remote_server.data.model.Organization
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.OrganizationRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetOrganizationUseCase(
    private val organizationRepository: OrganizationRepository
) {
    operator fun invoke(orgId: Long): Flow<Resource<Organization>> {
        return organizationRepository.getOrganizationById(orgId)
    }
}
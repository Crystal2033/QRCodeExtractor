package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.WorkspaceRepository
import com.crystal2033.qrextractor.core.model.WorkSpace
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class WorkspaceRepositoryImpl: WorkspaceRepository {
    override fun getWorkspaces(): Flow<Resource<List<WorkSpace>>> {
        TODO("Not yet implemented")
    }
}
package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person.WorkspaceApi
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.WorkspaceRepository
import com.crystal2033.qrextractor.core.model.WorkSpace
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WorkspaceRepositoryImpl(
    private val workspaceApi: WorkspaceApi
) : WorkspaceRepository {
    override fun getWorkspaces(): Flow<Resource<List<WorkSpace>>> = flow {
        emit(Resource.Loading())

        val listOfWorkspaces =
            workspaceApi.getWorkspaces().body()?.map { workSpaceDto -> workSpaceDto.toWorkSpace() }
        //TODO: add errors catch
        emit(Resource.Success(listOfWorkspaces))
    }
}
package com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.WorkspaceRepository
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.GetListOfAllToChooseUseCase
import com.crystal2033.qrextractor.core.model.WorkSpace
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetWorkspacesUseCase(
    private val workspaceRepository: WorkspaceRepository
): GetListOfAllToChooseUseCase<WorkSpace> {
    override fun invoke(): Flow<Resource<List<WorkSpace>>> {
        return workspaceRepository.getWorkspaces()
    }

}
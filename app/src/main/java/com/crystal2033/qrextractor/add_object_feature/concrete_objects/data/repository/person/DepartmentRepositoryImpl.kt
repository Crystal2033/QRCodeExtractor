package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.DepartmentRepository
import com.crystal2033.qrextractor.core.model.Department
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class DepartmentRepositoryImpl : DepartmentRepository {
    override fun getDepartments(): Flow<Resource<List<Department>>> {
        TODO("Not yet implemented")
    }
}
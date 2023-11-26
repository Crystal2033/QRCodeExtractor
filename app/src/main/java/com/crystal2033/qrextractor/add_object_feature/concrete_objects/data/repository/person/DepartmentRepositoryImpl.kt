package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person.DepartmentApi
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.DepartmentRepository
import com.crystal2033.qrextractor.core.model.Department
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DepartmentRepositoryImpl(
    private val departmentApi: DepartmentApi
) : DepartmentRepository {
    override fun getDepartments(): Flow<Resource<List<Department>>> = flow {
        emit(Resource.Loading())

        val listOfDepartment: List<Department>? =
            departmentApi.getDepartments().body()
                ?.map { departmentDto -> departmentDto.toDepartment() }

        //TODO: add errors catch
        emit(Resource.Success(listOfDepartment))
    }
}
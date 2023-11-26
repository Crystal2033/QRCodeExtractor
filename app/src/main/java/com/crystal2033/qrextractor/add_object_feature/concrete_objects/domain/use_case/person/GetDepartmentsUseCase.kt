package com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.DepartmentRepository
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.GetListOfAllToChooseUseCase
import com.crystal2033.qrextractor.core.model.Department
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetDepartmentsUseCase(
    private val departmentRepository: DepartmentRepository
): GetListOfAllToChooseUseCase<Department> {
    override fun invoke(): Flow<Resource<List<Department>>> {
        return departmentRepository.getDepartments()
    }
}
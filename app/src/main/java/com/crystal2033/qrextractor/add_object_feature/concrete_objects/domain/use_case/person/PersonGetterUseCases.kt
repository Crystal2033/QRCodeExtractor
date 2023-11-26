package com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person

data class PersonGetterUseCases(
    val addNewPersonUseCase: AddNewPersonUseCase,
    val getDepartmentsUseCase: GetDepartmentsUseCase,
    val getTitlesUseCase: GetTitlesUseCase,
    val getWorkspacesUseCase: GetWorkspacesUseCase
)

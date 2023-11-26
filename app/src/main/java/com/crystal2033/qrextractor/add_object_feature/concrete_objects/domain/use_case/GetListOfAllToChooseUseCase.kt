package com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case

import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetListOfAllToChooseUseCase<T> {
    operator fun invoke(): Flow<Resource<List<T>>>
}
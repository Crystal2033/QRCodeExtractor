package com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person

import com.crystal2033.qrextractor.core.model.Title
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface TitleRepository {
    fun getTitles(): Flow<Resource<List<Title>>>

}
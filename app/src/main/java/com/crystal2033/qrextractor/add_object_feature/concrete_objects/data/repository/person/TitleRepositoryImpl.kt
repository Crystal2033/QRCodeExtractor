package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.TitleRepository
import com.crystal2033.qrextractor.core.model.Title
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class TitleRepositoryImpl : TitleRepository {
    override fun getTitles(): Flow<Resource<List<Title>>> {
        TODO("Not yet implemented")
    }
}
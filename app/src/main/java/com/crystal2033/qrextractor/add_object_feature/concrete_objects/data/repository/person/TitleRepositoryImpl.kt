package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person.TitleApi
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.TitleRepository
import com.crystal2033.qrextractor.core.model.Title
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TitleRepositoryImpl(
    private val titleApi: TitleApi
) : TitleRepository {
    override fun getTitles(): Flow<Resource<List<Title>>> = flow{
        emit(Resource.Loading())

        val listOfTitles = titleApi.getTitles().body()?.map {titleDto -> titleDto.toTitle() }
        //TODO: add errors catch
        emit(Resource.Success(listOfTitles))
    }
}
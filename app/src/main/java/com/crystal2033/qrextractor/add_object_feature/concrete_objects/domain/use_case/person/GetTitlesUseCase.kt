package com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.TitleRepository
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.GetListOfAllToChooseUseCase
import com.crystal2033.qrextractor.core.model.Title
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetTitlesUseCase(
    private val titleRepository: TitleRepository
): GetListOfAllToChooseUseCase<Title> {
    override fun invoke(): Flow<Resource<List<Title>>> {
        return titleRepository.getTitles()
    }

}
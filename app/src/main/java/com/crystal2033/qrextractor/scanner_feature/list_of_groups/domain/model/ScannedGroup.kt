package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model

data class ScannedGroup(
    val id: Long = 0L,
    val groupName: String? = null,
    val listOfScannedObjects: MutableList<ScannedObjectWithIdInLocalDB> = mutableListOf()
) {
}
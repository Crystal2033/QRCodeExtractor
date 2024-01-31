//package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository
//
//import com.crystal2033.qrextractor.core.util.Resource
//import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupWithScannedObjectsRel
//import kotlinx.coroutines.flow.Flow
//
//interface GetScannedGroupWithObjectsRepository {
//    fun getScannedGroupWithObjects(scannedGroupId: Long): Flow<Resource<ScannedGroupWithScannedObjectsRel?>>
//}
package com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository

import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import kotlinx.coroutines.flow.Flow

//TODO: probably need to move in core of scanned_feature
interface ScannedGroupCreatorRepository {
    fun insertScannedGroupInDb(
        scannedObjectList: List<QRScannableData>,
        user: User,
        groupName: String
    ): Flow<Resource<Unit>>


}
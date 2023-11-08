package com.crystal2033.qrextractor.core.localdb.repository.impl

import com.crystal2033.qrextractor.core.localdb.UserDao
import com.crystal2033.qrextractor.core.localdb.repository.ScannedObjectsListRepository
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupWithScannedObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ScannedObjectsListRepositoryImpl(
    private val dao: UserDao
) : ScannedObjectsListRepository {

    override fun getUserScannedGroupsWithScannedObjects(userId: Int): Flow<Resource<List<ScannedGroupWithScannedObjects>>> =
        flow {
            //emit(Resource.Loading())
//            Log.i(LOG_TAG_NAMES.INFO_TAG, "Before add user")
//            val user = UserEntity(
//                username = "Crystal2033",
//                password = "12345"
//            )
//            user.userId = dao.saveUser(user)
//            Log.i(LOG_TAG_NAMES.INFO_TAG, "Before add user with id: ${user.userId}")


        }
}
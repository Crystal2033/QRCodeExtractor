package com.crystal2033.qrextractor.scanner_feature.general.repository_impl

import android.content.Context
import com.crystal2033.qrextractor.core.User
import com.crystal2033.qrextractor.core.localdb.ScannedGroupDao
import com.crystal2033.qrextractor.core.localdb.ScannedObjectDao
import com.crystal2033.qrextractor.core.localdb.UserDao
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.repository.UserWithScannedGroupsRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupEntity
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupObjectCrossRef
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedObjectEntity
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.ScannedGroupCreatorRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.UserWithScannedGroupsAndObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ScannedGroupRepositoryImpl(
    private val scannedObjectDao: ScannedObjectDao,
    private val scannedGroupDao: ScannedGroupDao,
    private val userDao: UserDao,
    private val context: Context
) : ScannedGroupCreatorRepository, UserWithScannedGroupsRepository {

    override fun insertScannedGroupInDb(
        scannedObjectList: List<QRScannableData>,
        user: User,
        groupName: String
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        //val testUserId = userDao.saveUser(UserEntity(username = user.name, password = "12345"))

        val userEntity = userDao.getUserById(user.id)
        val scannedGroupEntity = ScannedGroupEntity(groupName, userEntity.userId)

        val scannedGroupId = scannedGroupDao.saveScannedGroup(scannedGroupEntity)
        for (scannedObject in scannedObjectList) {
            val scannedObjectEntity = ScannedObjectEntity(
                scannedObject.getDatabaseTableName().getLabel(context),
                scannedObject.getDatabaseID()
            )

            val scannedObjectId = scannedObjectDao.saveScannedObject(scannedObjectEntity)

            val crossGroupObjectRef = ScannedGroupObjectCrossRef(
                scannedObjectId = scannedObjectId,
                scannedGroupId = scannedGroupId
            )

            scannedGroupDao.saveScannedGroupCrossRef(crossGroupObjectRef)
        }
        emit(Resource.Success(Unit))
    }

    override fun getScannedGroupsForUserFromDb(user: User): Flow<Resource<UserWithScannedGroupsAndObjects>> = flow {
        emit(Resource.Loading())
        //val testUserId = userDao.saveUser(UserEntity(username = user.name, password = "12345"))
        val usersScannedGroups = userDao.getUserWithScannedGroupsAndObjects(user.id)
//        val userEntity = userDao.getUserById(user.id)
        emit(Resource.Success(usersScannedGroups))
    }


}
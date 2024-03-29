package com.crystal2033.qrextractor.scanner_feature.general.repository_impl

import android.content.Context
import com.crystal2033.qrextractor.core.localdb.ScannedGroupDao
import com.crystal2033.qrextractor.core.localdb.ScannedObjectDao
import com.crystal2033.qrextractor.core.localdb.UserDao
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.UserWithScannedGroups
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository.DeleteObjectItemInScannedGroupRepository
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository.DeleteScannedGroupRepository
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository.UserWithScannedGroupsRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupEntity
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupObjectCrossRef
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedObjectEntity
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.ScannedGroupCreatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class ScannedGroupRepositoryImpl(
    private val scannedObjectDao: ScannedObjectDao,
    private val scannedGroupDao: ScannedGroupDao,
    private val userDao: UserDao,
    private val context: Context
) : ScannedGroupCreatorRepository, UserWithScannedGroupsRepository,
    DeleteScannedGroupRepository, DeleteObjectItemInScannedGroupRepository {

    override fun insertScannedGroupInDb(
        scannedObjectList: List<QRScannableData>,
        user: User,
        groupName: String
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        //val testUserId = userDao.saveUser(UserEntity(username = user.name, password = "12345"))
        val userEntity = userDao.getUserById(user.idLocalDB)
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


    override fun getScannedGroupsForUserFromDb(userId: Long): Flow<Resource<UserWithScannedGroups>> =
        flow {
            emit(Resource.Loading())
            val usersScannedGroups = userDao.getUserWithScannedGroupsAndObjects(userId)
            usersScannedGroups?.let {
                val convertedValue = it.toUserWithScannedGroups()
                emit(Resource.Success(convertedValue))
            } ?: emit(Resource.Error("User with ID = $userId not found.}"))

        }

    //Query already is a transaction
    override fun deleteScannedGroupWithObjects(scannedGroup: ScannedGroup): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        for (scannedObject in scannedGroup.listOfScannedObjects) {
            scannedObjectDao.deleteScannedObjectFromDB(scannedObject.idInLocalDB)
        }
        scannedGroupDao.deleteScannedGroup(scannedGroup.id)
        scannedGroupDao.deleteScannedGroupInCrossRef(scannedGroup.id)
        emit(Resource.Success(Unit))
    }

    override fun deleteObjectItemInScannedGroup(
        scannedGroupId: Long,
        scannedObjectId: Long
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        scannedObjectDao.deleteScannedObjectFromDB(scannedObjectId)
        scannedGroupDao.deleteObjectFromScannedGroup(scannedGroupId, scannedObjectId)
        emit(Resource.Success(Unit))
    }


}
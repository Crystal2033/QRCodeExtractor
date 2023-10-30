package com.crystal2033.qrextractor.scanner_feature.data.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class ScannedGroup(
    val groupName: String,
    //val date: LocalDate,
    val userCreatorId: Long,

    @PrimaryKey(autoGenerate = true)
    var scannedGroupId: Long= 0
)
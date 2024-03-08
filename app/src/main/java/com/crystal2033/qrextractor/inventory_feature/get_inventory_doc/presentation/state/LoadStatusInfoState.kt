package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.state

import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.LoadStatus

data class LoadStatusInfoState(
    val loadStatus: LoadStatus = LoadStatus.NO_FILE,
    val message: String = ""
)

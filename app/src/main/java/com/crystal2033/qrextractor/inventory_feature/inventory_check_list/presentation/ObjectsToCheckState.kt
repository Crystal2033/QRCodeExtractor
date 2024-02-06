package com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation

import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization.ObjectInInventarizedFile

data class ObjectsToCheckState(
    var listOfObjects: MutableList<ObjectInInventarizedFile> = arrayListOf(),
    var isLoading : Boolean = false
)
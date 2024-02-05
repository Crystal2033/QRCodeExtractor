package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization

data class ExcelCellInfo(
    val row: Int = ERROR_POS,
    val column: Int = ERROR_POS
){
    companion object{
        const val ERROR_POS = -1
    }
}

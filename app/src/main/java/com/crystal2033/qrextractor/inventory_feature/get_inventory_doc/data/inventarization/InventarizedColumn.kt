package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization

data class InventarizedColumn(
    val inventarizedColumnName: InventarizedColumnNames = InventarizedColumnNames.UNKNOWN,
    val excelCellInfo: ExcelCellInfo = ExcelCellInfo()
)
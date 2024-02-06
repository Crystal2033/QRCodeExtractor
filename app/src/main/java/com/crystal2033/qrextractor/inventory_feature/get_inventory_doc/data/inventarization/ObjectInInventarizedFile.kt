package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization

import org.apache.poi.ss.usermodel.Sheet
import java.math.BigDecimal
import java.util.Date

//data class FieldAndExcelPosition<T>(var fieldValue: T, val excelPos: ExcelCellInfo) {
//
//}

data class ObjectInInventarizedFile(
    var ordNumber: Long = 0L,
    var objectName: String = "",
    var docRentName: String = "",
    var docRentDate: Date = Date(),
    var docRentNumber: String = "",
    var releaseYear: Int = 0,
    var invNumber: String = "",
    var factoryNumber: String = "",
    var passportNumber: String = "",
    var factQuantityAndPosInExcel: FieldAndExcelPosition<Int> = FieldAndExcelPosition(
        0,
        ExcelCellInfo()
    ),
    var factPriceAndPosInExcel: FieldAndExcelPosition<BigDecimal> = FieldAndExcelPosition(
        BigDecimal.ZERO,
        ExcelCellInfo()
    ),
    //var factQuantity: Int = 0,
    //var factPrice: BigDecimal = BigDecimal.ZERO,
    var accountantQuantity: Int = 0,
    var accountantPrice: BigDecimal = BigDecimal.ZERO,
    var pricePerOne: BigDecimal = BigDecimal.ZERO
) {
    init {
        if (accountantQuantity != 0) {
            pricePerOne = accountantPrice / accountantQuantity.toBigDecimal()
        }
    }

    fun incrementFactQuantity() {
        factQuantityAndPosInExcel.fieldValue++
        factPriceAndPosInExcel.fieldValue =
            pricePerOne.multiply(BigDecimal(factQuantityAndPosInExcel.fieldValue))
    }

    fun writeFactDataInExcel(workSheet: Sheet) {
        val factQuantityExcelPost = factQuantityAndPosInExcel.excelPos
        val factPriceExcelPost = factQuantityAndPosInExcel.excelPos
        workSheet.getRow(factQuantityExcelPost.row).getCell(factQuantityExcelPost.column)
            .setCellValue(factQuantityAndPosInExcel.fieldValue.toDouble())
        workSheet.getRow(factPriceExcelPost.row).getCell(factPriceExcelPost.column)
            .setCellValue(factPriceAndPosInExcel.fieldValue.toDouble())
    }

}
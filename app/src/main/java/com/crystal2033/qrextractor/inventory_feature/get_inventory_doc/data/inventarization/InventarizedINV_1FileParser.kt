package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.apache.poi.EncryptedDocumentException
import org.apache.poi.ss.usermodel.*
import org.crystal2033.inventarization.exception.FileNotValidException
import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal
import java.util.*

//for INV-1
class InventarizedINV_1FileParser(
    private val valueOfTableColumnsInDocument: Int = 13,
    private val mapWithColumnNumberToNameBijection: Map<Int, InventarizedColumnNames> = mapOf(
        1 to InventarizedColumnNames.ORD_NUMBER,
        2 to InventarizedColumnNames.OBJ_NAME,
        3 to InventarizedColumnNames.DOC_RENT_NAME,
        4 to InventarizedColumnNames.DOC_RENT_DATE,
        5 to InventarizedColumnNames.DOC_RENT_NUMBER,
        6 to InventarizedColumnNames.RELEASE_YEAR,
        7 to InventarizedColumnNames.INV_NUMBER,
        8 to InventarizedColumnNames.FACTORY_NUMBER,
        9 to InventarizedColumnNames.PASSPORT_NUMBER,
        10 to InventarizedColumnNames.FACT_QUANTITY,
        11 to InventarizedColumnNames.FACT_PRICE,
        12 to InventarizedColumnNames.ACCOUNTANT_QUANTITY,
        13 to InventarizedColumnNames.ACCOUNTANT_PRICE,
    ),
    private val listOfDocumentColumns: MutableList<InventarizedColumn> = MutableList(
        valueOfTableColumnsInDocument
    ) { InventarizedColumn() },
    val listOfObjects: MutableList<ObjectInInventarizedFile> = arrayListOf()
) {
    companion object {
        private const val BEGIN_TABLE_VALUE = "номерпопорядку"
    }

    @Throws(
        FileNotValidException::class, IOException::class, EncryptedDocumentException::class
    )
    fun init(inputStream: InputStream): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            val workbook = WorkbookFactory.create(inputStream)
            val workSheet = workbook.getSheetAt(0)
            initTableColumnsPositions(workSheet)
            setListOfObjects(workSheet)
        }
    }

    fun flushFactInventarizedDataInExcel(inputStream: InputStream) {
        val workbook = WorkbookFactory.create(inputStream)
        val workSheet = workbook.getSheetAt(0)
        for (invObject in listOfObjects) {
            invObject.writeFactDataInExcel(workSheet)
        }
    }

    //<Ищем стрелочку цифру рядом со стрелкой, чтобы потом определить позиции колонок>
    //Номер по порядку
    //
//->//1(номер кол)  2 (название)                  3   ...
    //1             Сервер-5
    //2             Сервер-6
    //3             Пистолет
    //4             Воздушный шар
    private fun getFirstTableColumnExcelCell(workSheet: Sheet): ExcelCellInfo {
        val rowIterator = workSheet.rowIterator()
        for (column in 0..5) {
            var rowNumber = 0
            while (rowIterator.hasNext()) {
                var cellValue = rowIterator.next().getCell(column)
                if (cellValue != null) {
                    val checkingValue = cellValue.toString().replace(Regex("(\\s|-)*"), "")
                    if (checkingValue.lowercase().contentEquals(BEGIN_TABLE_VALUE)) {
                        for (k in 1..15) {
                            cellValue = workSheet.getRow(rowNumber + k).getCell(column)

                            if (cellValue.cellType == CellType.NUMERIC) {
                                if (cellValue.numericCellValue.toInt() == 1) {
                                    return ExcelCellInfo(rowNumber + k, column)
                                }
                            } else {
                                if (cellValue.toString().contentEquals("1")) {
                                    return ExcelCellInfo(rowNumber + k, column)
                                }
                            }

                        }
                        return ExcelCellInfo()
                    }
                }
                rowNumber++
            }
        }
        return ExcelCellInfo()
    }

    private fun addNewDocumentColumn(
        currDocColumnCounter: Int,
        rowExcelPos: Int,
        columnExcelPos: Int
    ) {
        listOfDocumentColumns[currDocColumnCounter] = InventarizedColumn(
            inventarizedColumnName = mapWithColumnNumberToNameBijection.getOrDefault(
                currDocColumnCounter + 1,
                InventarizedColumnNames.UNKNOWN
            ),
            excelCellInfo = ExcelCellInfo(row = rowExcelPos, column = columnExcelPos)
        )
    }

    @Throws(FileNotValidException::class)
    private fun initTableColumnsPositions(workSheet: Sheet) {
        val startCellOfTable = getFirstTableColumnExcelCell(workSheet)
        if (startCellOfTable.row == ExcelCellInfo.ERROR_POS || startCellOfTable.column == ExcelCellInfo.ERROR_POS) {
            throw FileNotValidException("Begin of table not found.")
        }

        var lastCheckedColumn = startCellOfTable.column
        var currDocColumn = 0
        val cellIterator = workSheet.getRow(startCellOfTable.row).cellIterator()

        while (cellIterator.hasNext() && currDocColumn != valueOfTableColumnsInDocument) {
            val checkingCell = cellIterator.next()
            if (checkingCell.cellType == CellType.NUMERIC) {
                if (checkingCell.numericCellValue.toInt() == currDocColumn + 1) {
                    addNewDocumentColumn(currDocColumn, startCellOfTable.row, lastCheckedColumn)
                    currDocColumn++
                }
            } else {
                if (checkingCell.toString().contentEquals((currDocColumn + 1).toString())) {
                    addNewDocumentColumn(currDocColumn, startCellOfTable.row, lastCheckedColumn)
                    currDocColumn++
                }
            }

            lastCheckedColumn++
//            if (checkingCell != null) {
//
//            }

        }
        if (currDocColumn != valueOfTableColumnsInDocument) {
            throw FileNotValidException("Can not find column in document table. It is too far from previous column")
        }


    }

    private fun getCellFromRowByNeededColumn(
        row: Row,
        inventarizedColumnName: InventarizedColumnNames
    ): Cell? {
        return row.getCell(listOfDocumentColumns[inventarizedColumnName.columnNumber - 1].excelCellInfo.column)
    }

    private fun insertNewInventarizedObjectInListByRow(row: Row) {
        val cellForOrdNumber = getCellFromRowByNeededColumn(
            row,
            InventarizedColumnNames.ORD_NUMBER
        )!!

        val cellForReleaseYear = getCellFromRowByNeededColumn(
            row,
            InventarizedColumnNames.RELEASE_YEAR
        )!!

        val cellForInvNumber =
            getCellFromRowByNeededColumn(row, InventarizedColumnNames.INV_NUMBER)!!
        val cellForFactoryNumber =
            getCellFromRowByNeededColumn(row, InventarizedColumnNames.FACTORY_NUMBER)!!
        val cellForPassportNumber =
            getCellFromRowByNeededColumn(row, InventarizedColumnNames.FACTORY_NUMBER)!!

        val cellForAccountantQuantity =
            getCellFromRowByNeededColumn(row, InventarizedColumnNames.ACCOUNTANT_QUANTITY)!!
        val cellForAccountantPrice =
            getCellFromRowByNeededColumn(row, InventarizedColumnNames.ACCOUNTANT_PRICE)!!

        val cellForFactQuantity =
            getCellFromRowByNeededColumn(row, InventarizedColumnNames.FACT_QUANTITY)

        val cellForFactPrice =
            getCellFromRowByNeededColumn(row, InventarizedColumnNames.FACT_PRICE)


        listOfObjects.add(
            ObjectInInventarizedFile(
                ordNumber = if (cellForOrdNumber.cellType == CellType.NUMERIC) cellForOrdNumber.numericCellValue.toLong() else cellForOrdNumber.stringCellValue.toLong(),
                objectName = getCellFromRowByNeededColumn(
                    row,
                    InventarizedColumnNames.OBJ_NAME
                )!!.stringCellValue,
                docRentNumber = getCellFromRowByNeededColumn(
                    row,
                    InventarizedColumnNames.DOC_RENT_NUMBER
                )!!.stringCellValue,
                docRentName = getCellFromRowByNeededColumn(
                    row,
                    InventarizedColumnNames.DOC_RENT_NAME
                )!!.stringCellValue,
//                docRentDate = getCellFromRowByNeededColumn(row, InventarizedColumnNames.DOC_RENT_DATE).dateCellValue
//                    ?: Date(), //errors
                releaseYear = if (cellForReleaseYear.cellType == CellType.NUMERIC) cellForReleaseYear.numericCellValue.toInt()
                else if (cellForReleaseYear.stringCellValue.isEmpty()) 0 else cellForReleaseYear.stringCellValue.toInt(),
                invNumber = if (cellForInvNumber.cellType == CellType.NUMERIC) cellForInvNumber.numericCellValue.toString() else cellForInvNumber.stringCellValue,
                factoryNumber = if (cellForFactoryNumber.cellType == CellType.NUMERIC) cellForFactoryNumber.numericCellValue.toString() else cellForFactoryNumber.stringCellValue,
                passportNumber = if (cellForPassportNumber.cellType == CellType.NUMERIC) cellForPassportNumber.numericCellValue.toString() else cellForPassportNumber.stringCellValue,
                factQuantityAndPosInExcel = FieldAndExcelPosition(
                    cellForFactQuantity?.let {
                        if (cellForFactQuantity.cellType == CellType.NUMERIC)
                            cellForFactQuantity.numericCellValue.toInt()
                        else cellForFactQuantity.stringCellValue.toInt()
                    }
                        ?: 0,
                    listOfDocumentColumns[InventarizedColumnNames.FACT_QUANTITY.columnNumber - 1].excelCellInfo
                ),
                factPriceAndPosInExcel = FieldAndExcelPosition(
                    cellForFactPrice?.let {
                        if (cellForFactPrice.cellType == CellType.NUMERIC)
                            BigDecimal(cellForFactPrice.numericCellValue)
                        else BigDecimal(cellForFactPrice.stringCellValue)
                    } ?: BigDecimal.ZERO,
                    listOfDocumentColumns[InventarizedColumnNames.FACT_PRICE.columnNumber - 1].excelCellInfo
                ),
                accountantQuantity = if (cellForAccountantQuantity.cellType == CellType.NUMERIC) cellForAccountantQuantity.numericCellValue.toInt() else cellForAccountantQuantity.stringCellValue.toInt(),
                accountantPrice = BigDecimal(
                    if (cellForAccountantPrice.cellType == CellType.NUMERIC) cellForAccountantPrice.numericCellValue.toString() else cellForAccountantPrice.stringCellValue
                ),
            )
        )

    }

    private fun setListOfObjects(workSheet: Sheet) {
//        println("-------------------------------------")
//        for(columnInfo in listOfDocumentColumns){
//            println(workSheet.getRow(columnInfo.excelCellInfo.row).getCell(columnInfo.excelCellInfo.column))
//        }
//        println("-------------------------------------")

        val rowIterator = workSheet.rowIterator()
        var currentRow = 0
        while (rowIterator.hasNext() && currentRow != listOfDocumentColumns[0].excelCellInfo.row + 1) {///доходим до начала таблицы
            rowIterator.next()
            currentRow++
        }
        while (rowIterator.hasNext()) { //Ходим по строкам
            //println(rowIterator.next().getCell(0).toString())
            val row = rowIterator.next()
            val cellValue = getCellFromRowByNeededColumn(row, InventarizedColumnNames.ORD_NUMBER)
                ?: break
            if (!"(\\d)*(\\.)?(\\d)*".toRegex().matches(cellValue.toString())) {
                break
            }
            insertNewInventarizedObjectInListByRow(row)
            currentRow++
        }
    }


}
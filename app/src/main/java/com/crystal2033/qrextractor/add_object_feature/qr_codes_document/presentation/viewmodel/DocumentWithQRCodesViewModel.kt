package com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.Page
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.vm_view_communication.DocumentQRCodeStickersEvent
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.vm_view_communication.UIDocumentQRCodeStickersEvent
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStream
import kotlin.math.ceil
import kotlin.math.floor


class DocumentWithQRCodesViewModel @AssistedInject constructor(
    @Assisted val listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _eventFlow = Channel<UIDocumentQRCodeStickersEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    @AssistedFactory
    interface Factory {
        fun create(listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>): DocumentWithQRCodesViewModel
    }

    fun onEvent(event: DocumentQRCodeStickersEvent) {
        when (event) {
            is DocumentQRCodeStickersEvent.OnChangeQRCodeStickerSize -> {
                onValueChanged(event.oldQRCodeStickerInfo, event.newStickerSize)
            }


            is DocumentQRCodeStickersEvent.CreateDocumentByDirUriAndFileName -> {
                Log.i(
                    LOG_TAG_NAMES.INFO_TAG,
                    "Creating file: ${event.dirUri.path}/${event.fileName}"
                )
                viewModelScope.launch {

                    withContext(Dispatchers.IO) {

                        //creation of pdf file
                        try {
                            val contentResolver = context.contentResolver
                            try {
                                val pickedDir = DocumentFile.fromTreeUri(context, event.dirUri)
//                                val tmpFile =
//                                    pickedDir?.createFile("application/pdf", event.fileName)
                                var tmpFile = pickedDir?.findFile(event.fileName)
                                tmpFile?.delete()
                                tmpFile =
                                    pickedDir?.createFile("application/pdf", event.fileName)
                                val out: OutputStream? =
                                    contentResolver.openOutputStream(tmpFile!!.uri)

                                createPdfFile(2490, 3740, out!!)
                                out.flush()
                                out.close()

                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } catch (e: Exception) {
                            Log.e(LOG_TAG_NAMES.ERROR_TAG, e.message ?: "")
                        }
                        //creation of pdf file

                    }
                    sendUiEvent(UIDocumentQRCodeStickersEvent.OnFileCreatedSuccessfully(event.dirUri.path + event.fileName))
                }
                Log.i(
                    LOG_TAG_NAMES.INFO_TAG,
                    "Done"
                )

            }
        }
    }

    private fun createPdfFile(pageWidth: Int, pageHeight: Int, foutStream: OutputStream) {
        val pdfDocument = PdfDocument()
        val essentialNameMaxLength = 40

        val essentialNameShiftInCellX = 15
        val essentialNameShiftInCellY = 60

        val cellWidth = QRCodeStickerInfo.StickerSize.BIG.bitmapSize + 50
        val cellHeigth = QRCodeStickerInfo.StickerSize.BIG.bitmapSize + 50

        val maxObjectsInRow = floor(pageWidth.toDouble() / cellWidth.toDouble()).toInt()
        val maxValueOfRows = floor(pageHeight.toDouble() / cellHeigth.toDouble()).toInt()

        var cellOffsetX = 0
        var cellOffsetY = 0

        var qrCodeOffsetInCellX = 0
        var qrCodeOffsetInCellY = 0

        var essentialNameOffsetInCellX = 0
        var essentialNameOffsetInCellY = 0

        var invNumberOffsetInCellX = 0
        var invNumberOffsetInCellY = 0

        var canvas: Canvas? = null
        var pageInfo: PdfDocument.PageInfo? = null
        var page: Page? = null

        listOfQRCodes.forEachIndexed { i, qrInfo ->

            if (i % (maxObjectsInRow * maxValueOfRows) == 0) {
                page?.let { existingPage ->
                    pdfDocument.finishPage(existingPage)
                }

                pageInfo = PdfDocument.PageInfo.Builder(
                    pageWidth,
                    pageHeight,
                    i / (maxObjectsInRow * maxValueOfRows) + 1
                ).create()
                page = pdfDocument.startPage(pageInfo)

                canvas = page?.canvas

                drawDashedLines(
                    canvas,
                    cellWidth.toFloat(),
                    cellHeigth.toFloat(),
                    pageWidth,
                    pageHeight,
                    maxValueOfRows,
                    maxObjectsInRow
                )
            }

            cellOffsetX = (i % maxObjectsInRow) * cellWidth
            cellOffsetY = ((i % (maxObjectsInRow * maxValueOfRows)) / maxObjectsInRow) * cellHeigth

            qrCodeOffsetInCellX = (cellWidth - qrInfo.stickerSize.bitmapSize) / 2
            qrCodeOffsetInCellY = (cellHeigth - qrInfo.stickerSize.bitmapSize) / 2

            qrInfo.qrCode = Bitmap.createScaledBitmap(
                qrInfo.qrCode!!.asAndroidBitmap(),
                qrInfo.stickerSize.bitmapSize,
                qrInfo.stickerSize.bitmapSize,
                false
            ).asImageBitmap()

            canvas?.drawBitmap(
                qrInfo.qrCode!!.asAndroidBitmap(),
                Rect(0, 0, qrInfo.stickerSize.bitmapSize, qrInfo.stickerSize.bitmapSize),
                Rect(
                    qrCodeOffsetInCellX + cellOffsetX,
                    qrCodeOffsetInCellY + cellOffsetY,
                    qrCodeOffsetInCellX + qrInfo.stickerSize.bitmapSize + cellOffsetX,
                    qrCodeOffsetInCellY + qrInfo.stickerSize.bitmapSize + cellOffsetY
                ), null
            )


            val penPainter = Paint()
            penPainter.textSize = 28f



            essentialNameOffsetInCellX = cellOffsetX + essentialNameShiftInCellX
            essentialNameOffsetInCellY = cellOffsetY + essentialNameShiftInCellY
            canvas?.drawText(
                if (qrInfo.essentialName.length > essentialNameMaxLength) qrInfo.essentialName.substring(
                    0,
                    essentialNameMaxLength
                ) else qrInfo.essentialName,
                essentialNameOffsetInCellX.toFloat(),
                essentialNameOffsetInCellY.toFloat(),
                penPainter
            )

            penPainter.textSize = (qrInfo.stickerSize.bitmapSize.toFloat() / 8)
            invNumberOffsetInCellX = cellOffsetX + (qrCodeOffsetInCellX.toFloat() + 0.2f * qrInfo.stickerSize.bitmapSize.toFloat()).toInt()
            invNumberOffsetInCellY = cellOffsetY + cellHeigth - qrCodeOffsetInCellY
            canvas?.drawText(
                qrInfo.inventoryNumber,
                invNumberOffsetInCellX.toFloat(),
                invNumberOffsetInCellY.toFloat(),
                penPainter
            )


        }

        pdfDocument.finishPage(page)
        pdfDocument.writeTo(foutStream)

    }

    private fun drawDashedLines(
        canvas: Canvas?,
        cellWidth: Float,
        cellHeigth: Float,
        pageWidth: Int,
        pageHeight: Int,
        rowsCount: Int,
        columnsCount: Int
    ) {
        val paintLinesStyle = Paint()
        paintLinesStyle.style = Paint.Style.STROKE
        paintLinesStyle.pathEffect = DashPathEffect(floatArrayOf(2f, 2f), 0f)
        paintLinesStyle.strokeWidth = 4f

        for (rowLine in 0 until rowsCount - 1) {
            canvas?.drawLine(
                0f,
                cellHeigth * (rowLine + 1),
                pageWidth.toFloat() - 1,
                cellHeigth * (rowLine + 1),
                paintLinesStyle
            )
        }
        for (columnLine in 0 until columnsCount - 1) {
            canvas?.drawLine(
                cellWidth * (columnLine + 1),
                0f,
                cellWidth * (columnLine + 1),
                (pageHeight - 1).toFloat(),
                paintLinesStyle
            )
        }
    }

    private fun onValueChanged(
        oldQRStickerInfo: QRCodeStickerInfo,
        newStickerSize: QRCodeStickerInfo.StickerSize
    ) { //in onEvent
        val index = listOfQRCodes.indexOf(oldQRStickerInfo)
        listOfQRCodes[index] = listOfQRCodes[index].copy(
            qrCode = oldQRStickerInfo.qrCode,
            essentialName = oldQRStickerInfo.essentialName,
            inventoryNumber = oldQRStickerInfo.inventoryNumber,
            databaseObjectTypes = oldQRStickerInfo.databaseObjectTypes,
            stickerSize = newStickerSize
        )
    }

    private fun sendUiEvent(event: UIDocumentQRCodeStickersEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: DocumentWithQRCodesViewModel.Factory,
            listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(listOfQRCodes) as T
            }
        }
    }

}
package com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStream
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round


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

                    // withContext(Dispatchers.IO) {

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

                            createPdfFile(1080, 1920, out!!)
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

                    // }
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

        val pageNumber = 1;

        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
//        val paint = Paint()
//        paint.color = Color.GREEN
//        paint.textSize = 42.0F


        //canvas.drawText("Pavel Kulikov!!!", x, y, paint)
//        val maxObjectsInRow = 3
//        val maxValueOfRows = 5

        //val cellWidth = pageWidth / maxObjectsInRow
        //val cellHeigth = pageHeight / maxValueOfRows
        val cellWidth = QRCodeStickerInfo.StickerSize.BIG.bitmapSize + 50
        val cellHeigth = QRCodeStickerInfo.StickerSize.BIG.bitmapSize + 50

        val maxObjectsInRow = floor(pageWidth.toDouble() / cellWidth.toDouble()).toInt()
        val maxValueOfRows = ceil(pageHeight.toDouble() / cellHeigth.toDouble()).toInt()

            drawDashedLines(
            canvas,
            cellWidth.toFloat(),
            cellHeigth.toFloat(),
            pageWidth,
            pageHeight,
            maxValueOfRows,
            maxObjectsInRow
        )

        var cellOffsetX = 0
        var cellOffsetY = 0

        var qrCodeOffsetInCellX = 0
        var qrCodeOffsetInCellY = 0

        var essentialNameOffsetInCellX = 0
        var essentialNameOffsetInCellY = 0

        var invNumberOffsetInCellX = 0
        var invNumberOffsetInCellY = 0
        listOfQRCodes.forEachIndexed { i, qrInfo ->

            cellOffsetX = (i % maxObjectsInRow) * cellWidth
            cellOffsetY = (i / maxObjectsInRow) * cellHeigth

            qrCodeOffsetInCellX = (cellWidth - qrInfo.stickerSize.bitmapSize) / 2 + cellOffsetX
            qrCodeOffsetInCellY = (cellHeigth - qrInfo.stickerSize.bitmapSize) / 2 + cellOffsetY

            qrInfo.qrCode = Bitmap.createScaledBitmap(qrInfo.qrCode!!.asAndroidBitmap(), qrInfo.stickerSize.bitmapSize, qrInfo.stickerSize.bitmapSize, false).asImageBitmap()

            canvas.drawBitmap(
                qrInfo.qrCode!!.asAndroidBitmap(),
                Rect(0, 0, qrInfo.stickerSize.bitmapSize, qrInfo.stickerSize.bitmapSize),
                Rect(
                    qrCodeOffsetInCellX,
                    qrCodeOffsetInCellY,
                    qrCodeOffsetInCellX + qrInfo.stickerSize.bitmapSize,
                    qrCodeOffsetInCellY + qrInfo.stickerSize.bitmapSize
                ), null
            )


            essentialNameOffsetInCellX = cellOffsetX + 5
            essentialNameOffsetInCellY = cellOffsetY + 5

            invNumberOffsetInCellX = cellOffsetX + (cellWidth.toFloat() * 0.44f).toInt()
            invNumberOffsetInCellY = cellOffsetY + qrCodeOffsetInCellY +
                    qrInfo.stickerSize.bitmapSize + 10


        }



        pdfDocument.finishPage(page)
        pdfDocument.writeTo(foutStream)

    }

    private fun drawDashedLines(
        canvas: Canvas,
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
            canvas.drawLine(
                0f,
                cellHeigth * (rowLine + 1),
                pageWidth.toFloat() - 1,
                cellHeigth * (rowLine + 1),
                paintLinesStyle
            )
        }
        for (columnLine in 0 until columnsCount - 1) {
            canvas.drawLine(
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
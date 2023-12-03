package com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel.DocumentWithQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.vm_view_communication.DocumentQRCodeStickersEvent
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.vm_view_communication.UIDocumentQRCodeStickersEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.dialog_window.DialogInsertName
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QRCodeStickersView(
    viewModel: DocumentWithQRCodesViewModel
) {
    val isChosenDirectory = remember {
        mutableStateOf(false)
    }

//    val isNeedToShowGroupNameInsertDialog = remember {
//        mutableStateOf(false)
//    }
    val context = LocalContext.current
    val dirUri = remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
            dirUri.value = it
            val nameOfFile = "TestFile.pdf"
            viewModel.onEvent(DocumentQRCodeStickersEvent.CreateDocumentByDirUriAndFileName(dirUri.value!!, nameOfFile))
            //isChosenDirectory.value = true

        }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIDocumentQRCodeStickersEvent.OnFileCreatedSuccessfully -> {
                    Toast.makeText(context, "File created successfully,", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold {
            if (isChosenDirectory.value) {
                DialogInsertName(
                    isNeedToShowDialog = isChosenDirectory,
                    onAcceptButtonClicked = { fileName ->
                        viewModel.onEvent(
                            DocumentQRCodeStickersEvent.CreateDocumentByDirUriAndFileName(
                                dirUri.value!!,
                                fileName
                            )
                        )
                    },
                    title = "QR-code filename",
                    helpMessage = "Please set file name for created qr-codes.",
                    placeholderInTextField = "File name...",
                )
            }

            Column {
                Button(
                    onClick = {
                        launcher.launch(dirUri.value)
                        //val uri = Uri.parse("/tree/primary:Download/Inventory")
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(15.dp)
                ) {
                    Text(text = "Create document with QR-codes")
                }



                LazyVerticalGrid(
                    columns = GridCells.Adaptive(200.dp),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    items(viewModel.listOfQRCodes) { qrSticker ->
                        QRCodeStickerView(
                            qrCodeStickerInfo = qrSticker,
                            onSizeChanged = viewModel::onEvent
                        )
                    }
                }


            }
        }

    }


}

//val Purple200 = Color(0xFF0F9D58)
//val Purple500 = Color(0xFF0F9D58)
//val Purple700 = Color(0xFF3700B3)
//val Teal200 = Color(0xFF03DAC5)

// on below line we are adding different colors.
//val greenColor = Color(0xFF0F9D58)

// on below line we are creating a
// pdf generator composable function for ui.
//@Composable
//fun pdfGenerator(pathOfFile: String) {
//
//    // on below line we are creating a variable for
//    // our context and activity and initializing it.
//    val ctx = LocalContext.current
//    val activity = (LocalContext.current as? Activity)
//
//    // on below line we are checking permission
////    if (checkPermissions(ctx)) {
////        // if permission is granted we are displaying a toast message.
////        Toast.makeText(ctx, "Permissions Granted..", Toast.LENGTH_SHORT).show()
////    } else {
////        // if the permission is not granted
////        // we are calling request permission method.
////        requestPermission(activity!!)
////    }
//
//    // on below line we are creating a column for our ui.
//    Column(
//        // in this column we are adding a modifier for our
//        // column and specifying max width, height and size.
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .fillMaxSize()
//
//            // on below line we are adding padding
//            // from all sides to our column.
//            .padding(6.dp),
//
//        // on below line we are adding vertical
//        // arrangement for our column as center
//        verticalArrangement = Arrangement.Center,
//
//        // on below line we are adding
//        // horizontal alignment for our column.
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        // on below line we are creating a simple text as a PDF Generator.
//        Text(
//            // on below line we are setting text to our text
//            text = "PDF Generator",
//
//            // on below line we are
//            // setting color for our text
//            color = greenColor,
//
//            // on below line we are setting
//            // font weight for our text
//            fontWeight = FontWeight.Bold,
//
//            // on below line we are setting
//            // alignment for our text as center.
//            textAlign = TextAlign.Center,
//
//            // on below line we are
//            // setting font size for our text
//            fontSize = 20.sp
//        )
//
//        // on below line we are adding
//        // spacer between text and a button.
//        Spacer(modifier = Modifier.height(60.dp))
//
//        // on the below line we are creating a button.
//        Button(
//            // on below line we are adding a modifier
//            // to it and specifying max width to it.
//            modifier = Modifier
//                .fillMaxWidth()
//
//                // on below line we are adding
//                // padding for our button.
//                .padding(20.dp),
//
//            // on below line we are adding
//            // on click for our button.
//            onClick = {
//
//                // inside on click we are calling our
//                // generate PDF method to generate our PDF
//                generatePDF(ctx, pathOfFile)
//            }) {
//
//            // on the below line we are displaying a text for our button.
//            Text(modifier = Modifier.padding(6.dp), text = "Generate PDF")
//        }
//    }
//
//}

// on below line we are creating a generate PDF
// method which is use to generate our PDF file.
//@RequiresApi(Build.VERSION_CODES.KITKAT)
//fun generatePDF(context: Context, uri: Uri) {
//
//    // declaring width and height
//    // for our PDF file.
//    var pageHeight = 1120
//    var pageWidth = 792
//
//    // creating a bitmap variable
//    // for storing our images
//    //lateinit var bmp: Bitmap
//    //lateinit var scaledbmp: Bitmap
//
//    // creating an object variable
//    // for our PDF document.
//    var pdfDocument: PdfDocument = PdfDocument()
//
//    // two variables for paint "paint" is used
//    // for drawing shapes and we will use "title"
//    // for adding text in our PDF file.
//    var paint: Paint = Paint()
//    var title: Paint = Paint()
//
//    // on below line we are initializing our bitmap and scaled bitmap.
//    //bmp = BitmapFactory.decodeResource(context.resources, R.drawable.database)
//    //scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)
//
//
//    // we are adding page info to our PDF file
//    // in which we will be passing our pageWidth,
//    // pageHeight and number of pages and after that
//    // we are calling it to create our PDF.
//    var myPageInfo: PdfDocument.PageInfo? =
//        PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
//
//    // below line is used for setting
//    // start page for our PDF file.
//    var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)
//
//    // creating a variable for canvas
//    // from our page of PDF.
//    var canvas: Canvas = myPage.canvas
//
//    // below line is used to draw our image on our PDF file.
//    // the first parameter of our drawbitmap method is
//    // our bitmap
//    // second parameter is position from left
//    // third parameter is position from top and last
//    // one is our variable for paint.
//    //canvas.drawBitmap(scaledbmp, 56F, 40F, paint)
//
//    // below line is used for adding typeface for
//    // our text which we will be adding in our PDF file.
//    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
//
//    // below line is used for setting text size
//    // which we will be displaying in our PDF file.
//    title.textSize = 15F
//
//    // below line is sued for setting color
//    // of our text inside our PDF file.
//    title.setColor(ContextCompat.getColor(context, R.color.purple_200))
//
//    // below line is used to draw text in our PDF file.
//    // the first parameter is our text, second parameter
//    // is position from start, third parameter is position from top
//    // and then we are passing our variable of paint which is title.
//    canvas.drawText("A portal for IT professionals.", 209F, 100F, title)
//    canvas.drawText("Geeks for Geeks", 209F, 80F, title)
//    title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
//    title.setColor(ContextCompat.getColor(context, R.color.purple_200))
//    title.textSize = 15F
//
//    // below line is used for setting
//    // our text to center of PDF.
//    title.textAlign = Paint.Align.CENTER
//    canvas.drawText("This is sample document which we have created.", 396F, 560F, title)
//
//    // after adding all attributes to our
//    // PDF file we will be finishing our page.
//    pdfDocument.finishPage(myPage)
//
//    // below line is used to set the name of
//    // our PDF file and its path.
//
//
//    val file: File = File(Environment.getExternalStorageDirectory(), "Another_One.pdf")
//        //val file = File(Environment.getExternalStorageDirectory(), uri.path)
//
//    try {
//        // after creating a file name we will
//        // write our PDF file to that location.
//        pdfDocument.writeTo(FileOutputStream(file))
//
//        // on below line we are displaying a toast message as PDF file generated..
//        Toast.makeText(context, "PDF file generated..", Toast.LENGTH_SHORT).show()
//    } catch (e: Exception) {
//        // below line is used
//        // to handle error
//        e.printStackTrace()
//
//        // on below line we are displaying a toast message as fail to generate PDF
//        Toast.makeText(context, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
//            .show()
//    }
//    // after storing our pdf to that
//    // location we are closing our PDF file.
//    pdfDocument.close()
//}

//fun checkPermissions(context: Context): Boolean {
//    // on below line we are creating a variable for both of our permissions.
//
//    // on below line we are creating a variable for writing to external storage permission
//    var writeStoragePermission = ContextCompat.checkSelfPermission(
//        context,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE
//    )
//
//    // on below line we are creating a variable for
//    // reading external storage permission
//    var readStoragePermission = ContextCompat.checkSelfPermission(
//        context,
//        Manifest.permission.READ_EXTERNAL_STORAGE
//    )
//
//    // on below line we are returning true if both the
//    // permissions are granted and returning false if permissions are not granted.
//    return writeStoragePermission == PackageManager.PERMISSION_GRANTED && readStoragePermission == PackageManager.PERMISSION_GRANTED
//}
//
//// on below line we are creating a function to request permission.
//fun requestPermission(activity: Activity) {
//
//    // on below line we are requesting read and write to
//    // storage permission for our application.
//    ActivityCompat.requestPermissions(
//        activity,
//        arrayOf(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        ), 101
//    )
//}
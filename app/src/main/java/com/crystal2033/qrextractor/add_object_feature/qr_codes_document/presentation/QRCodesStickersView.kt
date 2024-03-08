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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel.DocumentWithQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.vm_view_communication.DocumentQRCodeStickersEvent
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.vm_view_communication.UIDocumentQRCodeStickersEvent
import com.crystal2033.qrextractor.core.util.GetStringNotInComposable
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

    val isNeedToShowGroupNameInsertDialog = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val dirUri = remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
            dirUri.value = it
            isChosenDirectory.value = true
        }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIDocumentQRCodeStickersEvent.OnFileCreatedSuccessfully -> {
                    Toast.makeText(context, GetStringNotInComposable(context, R.string.file_created_translate), Toast.LENGTH_LONG).show()
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
                    title = stringResource(id = R.string.qr_code_filename_translate),
                    helpMessage = stringResource(id = R.string.set_qr_code_name_translate),
                    placeholderInTextField = stringResource(id = R.string.file_name_translate) ,
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
                    Text(text = stringResource(id = R.string.create_document_qr_code_translate))
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
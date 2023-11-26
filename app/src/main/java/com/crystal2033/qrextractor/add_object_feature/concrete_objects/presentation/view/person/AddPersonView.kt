package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.person

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.person.AddPersonViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.model.Person

@Composable
fun AddPersonView(
    viewModel: AddPersonViewModel,
    qrCodeStickerInfo: QRCodeStickerInfo
) {
    val person = remember {
        mutableStateOf(Person())
    }

    Box(){


        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    //onAddObjectClicked(qrCodeStickerInfoState)
                }) {
                Text(text = "Add")
            }
            Button(onClick = {
                //navController.navigate(context.getString(R.string.menu_add_route))
            }) {
                Text(text = "Cancel")
            }
        }
    }

}
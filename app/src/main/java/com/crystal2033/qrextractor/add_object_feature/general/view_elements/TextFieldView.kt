package com.crystal2033.qrextractor.add_object_feature.general.view_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextFieldView(
    fieldHint: String,
    onValueChanged: (String) -> Unit,
    pattern: Regex = Regex("(\\w|\\d)*"),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,

    ) {

    val fieldValue = remember {
        mutableStateOf("")
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = verticalAlignment,
            horizontalArrangement = horizontalArrangement
        ) {
            Text(
                text = fieldHint,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                modifier = Modifier.align(verticalAlignment)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = verticalAlignment,
            horizontalArrangement = horizontalArrangement
        ) {
            OutlinedTextField(
                value = fieldValue.value,
                onValueChange = {
                    if (it.matches(pattern)) {
                        fieldValue.value = it
                        onValueChanged(fieldValue.value)
                    }

                },
                modifier = Modifier.align(verticalAlignment)
            )
        }

    }
}

@Composable
@Preview
fun TextFieldPreview() {
//    val textToInsert = remember {
//        mutableStateOf("")
//    }
//    TextFieldView("Test string", textToInsert){
//
//    }
}
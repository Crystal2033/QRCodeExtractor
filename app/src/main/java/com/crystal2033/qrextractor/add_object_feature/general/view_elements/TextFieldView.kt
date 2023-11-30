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
import androidx.compose.runtime.MutableState
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
    fieldName: String,
    fieldValue: MutableState<String>,
    pattern: Regex = Regex("(\\w|\\d)*"),
    actionAfterValueChanged: () -> Unit = {}

) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = fieldName,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            OutlinedTextField(
                value = fieldValue.value,
                onValueChange = {
                    if (it.matches(pattern)) {
                        fieldValue.value = it
                        actionAfterValueChanged()
                    }

                },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

    }
}

@Composable
@Preview
fun TextFieldPreview() {
    val textToInsert = remember {
        mutableStateOf("")
    }
    TextFieldView("Test string", textToInsert){

    }
}
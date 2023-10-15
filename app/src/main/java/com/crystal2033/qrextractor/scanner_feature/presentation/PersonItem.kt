package com.crystal2033.qrextractor.scanner_feature.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.scanner_feature.domain.model.Department
import com.crystal2033.qrextractor.scanner_feature.domain.model.Person
import com.crystal2033.qrextractor.scanner_feature.domain.model.Title
import com.crystal2033.qrextractor.scanner_feature.domain.model.WorkSpace

@Composable
fun PersonInfo(
    person: Person?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "First name: ${person?.firstName ?: "Unknown first name"}",
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Second name: ${person?.secondName ?: "Unknown second name"}",
                color = Color.White
            )
        }
    }

}

@Composable
@Preview
fun PersonItemPreview() {
    val person = Person(
        id = 1,
        department = Department(
            name = "Google",
            id = 2
        ),
        image = "Image",
        firstName = "Paul",
        secondName = "Kulikov",
        title = Title(
            id = 3,
            name = "Director"
        ),
        workSpace = WorkSpace(
            id = 5
        )
    )
    PersonInfo(person)

}
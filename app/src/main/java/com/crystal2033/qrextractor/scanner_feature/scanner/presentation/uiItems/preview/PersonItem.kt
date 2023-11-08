package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.uiItems.preview

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Department
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Person
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Title
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.WorkSpace


@Composable
fun PersonInfo(
    person: Person?,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color(0xff1c1b1f)
//        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "PERSON",
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    person?.image?.let { imageBitmap ->
                        Image(
                            bitmap = imageBitmap,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ShowId(
                        person?.id ?: 0,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    FieldNameAndValue(
                        "Name",
                        "${person?.firstName} ${person?.secondName}",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FieldNameAndValue(
                        "Work company",
                        person?.department?.name ?: "No work place"
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    FieldNameAndValue(
                        "Title",
                        person?.title?.name ?: "No title"
                    )
                }

            }
        }


    }

}

@Composable
fun ShowId(id: Long, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.database),
            contentDescription = "database",
            tint = Color.LightGray,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "id: ", color = Color.LightGray)
        Text(text = id.toString(), color = Color.LightGray)
    }
}

@Composable
fun FieldNameAndValue(fieldName: String, fieldValue: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = fieldName, color = Color.Gray, fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = fieldValue, color = Color.White, fontSize = 20.sp)
    }
}

@Composable
@Preview
fun PersonItemPreview() {
    val bitmap = BitmapFactory.decodeFile("D:\\Картинки\\blue-space.jpg")
    val person = Person(
        id = 1,
        department = Department(
            name = "Google",
            id = 2
        ),
        image = bitmap.asImageBitmap(),
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
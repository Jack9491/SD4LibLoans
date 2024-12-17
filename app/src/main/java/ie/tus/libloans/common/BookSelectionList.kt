package ie.tus.libloans.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookSelectionList(
    books: List<String>,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    val selectedBooks = remember { mutableStateListOf<Int>() }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFBC02D), shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(books) { index, book ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Book number and title
                Column {
                    Text(
                        text = "${index + 1}. $book",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                // Checkbox for selection
                Checkbox(
                    checked = selectedBooks.contains(index),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedBooks.add(index)
                        } else {
                            selectedBooks.remove(index)
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Gray,
                        uncheckedColor = Color.White
                    )
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = { onButtonClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBC02D)),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

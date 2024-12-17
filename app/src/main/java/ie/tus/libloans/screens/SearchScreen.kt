package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(navController: NavHostController) {
    // Mock book data
    val books = remember {
        listOf(
            "Book 1", "Book 2", "Book 3", "Book 4", "Book 5", "Book 6", "Book 7", "Book 8"
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchScreenContent(
            books = books,
            onDetailsClick = { book ->
                // Navigate to the book details screen
                navController.navigate("bookDetailsScreen")
            }
        )
    }
}

@Composable
private fun SearchScreenContent(
    books: List<String>,
    onDetailsClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Dark background
            .padding(16.dp)
    ) {
        // Top Bar
        Text(
            text = "Search Books",
            color = Color(0xFFFBC02D), // Yellow color
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Bar
        SearchBar()

        Spacer(modifier = Modifier.height(16.dp))

        // Book List
        BookList(books = books, onDetailsClick = onDetailsClick)
    }
}

@Composable
private fun SearchBar() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text("Search...", color = Color.Gray) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(56.dp)
    )
}

@Composable
private fun BookList(books: List<String>, onDetailsClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(books) { index, book ->
            BookRow(index = index + 1, bookName = book, onDetailsClick = { onDetailsClick(book) })
        }
    }
}

@Composable
private fun BookRow(index: Int, bookName: String, onDetailsClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBC02D)), // Yellow background
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "$index",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = bookName,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Button(
                onClick = onDetailsClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "View Book Details", color = Color.White)
            }
        }
    }
}


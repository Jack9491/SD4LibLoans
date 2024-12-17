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

// SearchScreen - Entry point for searching books
@Composable
fun SearchScreen(navController: NavHostController) {
    // Mock book data
    val books = remember {
        listOf(
            "Book 1", "Book 2", "Book 3", "Book 4", "Book 5", "Book 6", "Book 7", "Book 8"
        )
    }

    // Main Surface Container
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

// SearchScreenContent - Displays the search bar and list of books
@Composable
private fun SearchScreenContent(
    books: List<String>,              // List of books to display
    onDetailsClick: (String) -> Unit  // Callback for viewing book details
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Dark background
            .padding(16.dp)
    ) {
        // Screen Title
        Text(
            text = "Search Books",
            color = Color(0xFFFBC02D), // Yellow color
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Bar Component
        SearchBar()

        Spacer(modifier = Modifier.height(16.dp))

        // Book List Component
        BookList(books = books, onDetailsClick = onDetailsClick)
    }
}

// SearchBar - A text field for entering search input
@Composable
private fun SearchBar() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) } // State for search input

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it }, // Update the search input
        placeholder = { Text("Search...", color = Color.Gray) },
        leadingIcon = { // Search icon on the left
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp), // Rounded corners
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

// BookList - Displays a list of books using LazyColumn
@Composable
private fun BookList(
    books: List<String>,              // List of books
    onDetailsClick: (String) -> Unit  // Callback for viewing book details
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Generate rows for each book
        itemsIndexed(books) { index, book ->
            BookRow(
                index = index + 1, // Row number
                bookName = book,
                onDetailsClick = { onDetailsClick(book) }
            )
        }
    }
}

// BookRow - Displays an individual book row with details button
@Composable
private fun BookRow(
    index: Int,             // Book number
    bookName: String,       // Book name
    onDetailsClick: () -> Unit // Callback when 'View Book Details' is clicked
) {
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
            // Book Index and Name
            Column {
                Text(
                    text = "$index", // Display book number
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = bookName, // Display book name
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            // Button to view book details
            Button(
                onClick = onDetailsClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "View Book Details", color = Color.White)
            }
        }
    }
}

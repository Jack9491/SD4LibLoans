package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ie.tus.libloans.common.BookSelectionList

// ReturnsScreen - Entry point for returning books
@Composable
fun ReturnsScreen(navController: NavHostController) {
    // Mock data: List of books available for return
    val returnBooks = remember {
        mutableStateListOf("Book 1 is due to be returned", "Book 2 is overdue")
    }

    // Main surface that hosts the content
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ReturnsScreenContent(
            returnBooks = returnBooks, // Pass the list of books to content
            onReturnBooksClick = {
                // TODO: Logic for returning books
            }
        )
    }
}

// ReturnsScreenContent - UI content for returning books
@Composable
private fun ReturnsScreenContent(
    returnBooks: List<String>,     // List of books to display
    onReturnBooksClick: () -> Unit // Callback when the return button is clicked
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Dark background
            .padding(16.dp)
    ) {
        // Screen Title
        Text(
            text = "Return a Book",
            color = Color(0xFFFBC02D), // Yellow color
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Book Selection List
        BookSelectionList(
            books = returnBooks,             // List of books to display
            buttonText = "Return Selected Books", // Button text for returning
            onButtonClick = onReturnBooksClick   // Callback for button click
        )
    }
}

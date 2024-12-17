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

// LoanScreen - Entry point for loaning books
@Composable
fun LoanScreen(navController: NavHostController) {
    // Mock data: List of books available for loaning
    val loanBooks = remember {
        mutableStateListOf("Book 1", "Book 2", "Book 3", "Book 4")
    }

    // Main surface that hosts the content
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LoanScreenContent(
            loanBooks = loanBooks, // Pass book list
            onLoanBooksClick = {
                // TODO: Loan action logic
            }
        )
    }
}

// LoanScreenContent - Displays book list for loaning
@Composable
private fun LoanScreenContent(
    loanBooks: List<String>,     // List of books to display
    onLoanBooksClick: () -> Unit // Callback when the loan button is clicked
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Dark background
            .padding(16.dp)
    ) {
        // Screen Title
        Text(
            text = "Loan a Book",
            color = Color(0xFFFBC02D), // Yellow color
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // List of books with a button to loan selected books
        BookSelectionList(
            books = loanBooks,             // Books to display
            buttonText = "Loan Selected Books", // Button text
            onButtonClick = onLoanBooksClick   // Loan button action
        )
    }
}

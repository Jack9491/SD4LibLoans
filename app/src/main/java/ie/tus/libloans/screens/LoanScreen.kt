package ie.tus.libloans.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoanScreen(navController: NavHostController) {
    // Mock data
    val loanBooks = remember {
        mutableStateListOf("Book 1", "Book 2", "Book 3", "Book 4")
    }
    val returnBooks = remember {
        mutableStateListOf("Book 1 is due to be returned")
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LoanScreenContent(
            loanBooks = loanBooks,
            returnBooks = returnBooks,
            onLoanBooksClick = {
                // Handle loan action here
            },
            onReturnBooksClick = {
                // Handle return action here
            }
        )
    }
}

@Composable
private fun LoanScreenContent(
    loanBooks: List<String>,
    returnBooks: List<String>,
    onLoanBooksClick: () -> Unit,
    onReturnBooksClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Dark background
            .padding(16.dp)
    ) {
        // Top Bar
        Text(
            text = "Books",
            color = Color(0xFFFBC02D), // Yellow color
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Loan Books Section
        SectionTitle(title = "Loan a Book")
        BookSelectionList(
            books = loanBooks,
            buttonText = "Loan Selected Books",
            onButtonClick = onLoanBooksClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Return Books Section
        SectionTitle(title = "Return a Book")
        BookSelectionList(
            books = returnBooks,
            buttonText = "Return Selected Books",
            onButtonClick = onReturnBooksClick
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun BookSelectionList(
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
                        text = "${index + 1}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = book,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                // Checkbox for selection
                Checkbox(
                    checked = selectedBooks.contains(index),
                    onCheckedChange = {
                        if (it) selectedBooks.add(index) else selectedBooks.remove(index)
                    },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.White,
                        checkedColor = Color.Gray,
                        uncheckedColor = Color.Gray
                    )
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBC02D)),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = buttonText, color = Color.White)
    }
}
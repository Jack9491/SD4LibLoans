package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import ie.tus.libloans.R
import ie.tus.libloans.ui.theme.LibLoansTheme

// Main screen for displaying book details
@Composable
fun BookDetailsScreen(
    navController: NavHostController,
    bookTitle: String = "Harry Potter",
    bookDescription: String = "New HP Book",
    bookGenre: String = "Magic"
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BookDetailsScreenContent(
            bookTitle = bookTitle,
            bookDescription = bookDescription,
            bookGenre = bookGenre,
            onBackClick = { navController.popBackStack() }, // Handle back button
            onLoanClick = {
                // TODO loan book action logic
            }
        )
    }
}

// Content for BookDetailsScreen
@Composable
private fun BookDetailsScreenContent(
    bookTitle: String,
    bookDescription: String,
    bookGenre: String,
    onBackClick: () -> Unit,
    onLoanClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Dark background
            .padding(16.dp)
    ) {
        // Top Bar with Back Button and Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) { // Back Button
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color(0xFFFBC02D)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Book Details", // Screen Title
                color = Color(0xFFFBC02D),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Book Image Placeholder
        Box(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color(0xFFFBC02D), shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_book_placeholder),
                contentDescription = "Book Image",
                tint = Color.Black,
                modifier = Modifier.size(80.dp)
            )
        }
        Text(
            text = "Book Image",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card with Book Details
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFECECEC)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Book Title
                Text(
                    text = bookTitle,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Book Description
                Text(
                    text = "Book Description: $bookDescription",
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Book Genre
                Text(
                    text = "Book Genre: $bookGenre",
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Loan Book Button
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onLoanClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBC02D)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Loan Book",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

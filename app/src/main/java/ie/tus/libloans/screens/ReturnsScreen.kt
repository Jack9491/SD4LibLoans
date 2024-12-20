package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.NavHostController
import com.google.firebase.Timestamp

// Main composable function for the Returns screen
@Composable
fun ReturnsScreen(navController: NavHostController) {
    val firestore = FirebaseFirestore.getInstance() // Firestore instance
    val userId = FirebaseAuth.getInstance().currentUser?.uid // Current user ID
    var loanedBooks by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) } // State to hold loaned books

    // Fetch loaned books when the composable is first composed
    LaunchedEffect(userId) {
        if (userId != null) {
            firestore.collection("users").document(userId).collection("loans").get()
                .addOnSuccessListener { querySnapshot ->
                    loanedBooks = querySnapshot.documents.mapNotNull { it.data } // Map Firestore documents to data
                }
        }
    }

    // Display the UI content
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ReturnsScreenContent(
            loanedBooks = loanedBooks, // Pass loaned books to the content composable
            onReturnClick = { book ->
                // Handle book return
                if (userId != null) {
                    firestore.collection("users").document(userId).collection("loans")
                        .whereEqualTo("bookTitle", book["bookTitle"]) // Find the specific loan
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            for (document in querySnapshot.documents) {
                                firestore.collection("users").document(userId).collection("loans")
                                    .document(document.id).delete() // Delete the loan document
                            }
                            loanedBooks = loanedBooks.filterNot { it["bookTitle"] == book["bookTitle"] } // Update state
                        }
                }
            },
            onBackClick = { navController.popBackStack() } // Navigate back on back button click
        )
    }
}

// Composable function for displaying the Returns screen content
@Composable
private fun ReturnsScreenContent(
    loanedBooks: List<Map<String, Any>>, // List of loaned books
    onReturnClick: (Map<String, Any>) -> Unit, // Callback for returning a book
    onBackClick: () -> Unit // Callback for navigating back
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Dark background
            .padding(16.dp) // Add padding around the content
    ) {
        // Row for the back arrow and screen title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFFBC02D) // Yellow tint for the back arrow
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Screen title
            Text(
                text = "Return a Book",
                color = Color(0xFFFBC02D),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Display a message if there are no loaned books
        if (loanedBooks.isEmpty()) {
            Text(
                text = "You have no books to return.",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            // LazyColumn for displaying the list of loaned books
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp) // Spacing between items
            ) {
                items(loanedBooks) { book ->
                    ReturnBookRow(
                        title = book["bookTitle"]?.toString() ?: "Unknown Title", // Book title
                        borrowDate = (book["borrowDate"] as? Timestamp)?.toDate()?.toString() ?: "Unknown", // Borrow date
                        returnDate = book["returnDate"]?.toString() ?: "Unknown", // Return date
                        imageUrl = book["imageUrl"]?.toString() ?: "", // Image URL
                        onReturnClick = { onReturnClick(book) } // Callback for returning a book
                    )
                }
            }
        }
    }
}

// Composable function for displaying individual book rows
@Composable
private fun ReturnBookRow(
    title: String, // Book title
    borrowDate: String, // Borrow date
    returnDate: String, // Return date
    imageUrl: String, // Image URL
    onReturnClick: () -> Unit // Callback for returning a book
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBC02D)), // Yellow background
        shape = RoundedCornerShape(12.dp), // Rounded corners
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp) // Fixed height for cards
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display book image
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)), // Rounded corners for the image
                contentScale = ContentScale.Crop // Crop image to fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Display book details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Borrowed: $borrowDate",
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = "Due: $returnDate",
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }
            // Return button
            Button(
                onClick = onReturnClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = "Return", color = Color.White)
            }
        }
    }
}

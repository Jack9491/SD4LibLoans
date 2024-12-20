package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ReturnsScreen(navController: NavHostController) {
    val firestore = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    var loanedBooks by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }

    // Fetch loaned books from Firestore
    LaunchedEffect(userId) {
        if (userId != null) {
            firestore.collection("users").document(userId).collection("loans").get()
                .addOnSuccessListener { querySnapshot ->
                    loanedBooks = querySnapshot.documents.mapNotNull { it.data }
                }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ReturnsScreenContent(
            loanedBooks = loanedBooks,
            onReturnClick = { book ->
                if (userId != null) {
                    // Remove the book from Firestore and update the list
                    firestore.collection("users").document(userId).collection("loans")
                        .whereEqualTo("bookTitle", book["bookTitle"])
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            for (document in querySnapshot.documents) {
                                firestore.collection("users").document(userId).collection("loans")
                                    .document(document.id).delete()
                            }
                            // Update the list after deletion
                            loanedBooks = loanedBooks.filterNot { it["bookTitle"] == book["bookTitle"] }
                        }
                }
            }
        )
    }
}

@Composable
private fun ReturnsScreenContent(
    loanedBooks: List<Map<String, Any>>, // List of loaned books
    onReturnClick: (Map<String, Any>) -> Unit // Callback for returning a book
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp)
    ) {
        // Screen Title
        Text(
            text = "Return a Book",
            color = Color(0xFFFBC02D),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (loanedBooks.isEmpty()) {
            // Display a message if no books are loaned
            Text(
                text = "You have no books to return.",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(loanedBooks) { book ->
                    ReturnBookRow(
                        title = book["bookTitle"]?.toString() ?: "Unknown Title",
                        borrowDate = book["borrowDate"]?.toString() ?: "Unknown",
                        returnDate = book["returnDate"]?.toString() ?: "Unknown",
                        onReturnClick = { onReturnClick(book) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ReturnBookRow(
    title: String,
    borrowDate: String,
    returnDate: String,
    onReturnClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBC02D)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f) // Ensure text takes available space
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

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = onReturnClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.align(Alignment.CenterVertically) // Align the button vertically
            ) {
                Text(text = "Return", color = Color.White)
            }
        }
    }
}


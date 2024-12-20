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
fun LoanScreen(navController: NavHostController) {
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
        LoanScreenContent(
            loanedBooks = loanedBooks
        )
    }
}

@Composable
private fun LoanScreenContent(
    loanedBooks: List<Map<String, Any>> // List of loaned books
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp)
    ) {
        // Screen Title
        Text(
            text = "Your Loaned Books",
            color = Color(0xFFFBC02D),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display loaned books
        if (loanedBooks.isEmpty()) {
            // Display a message if no books are loaned
            Text(
                text = "You have no books currently loaned.",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(loanedBooks) { book ->
                    LoanedBookRow(
                        title = book["bookTitle"]?.toString() ?: "Unknown Title",
                        borrowDate = book["borrowDate"]?.toString() ?: "Unknown",
                        returnDate = book["returnDate"]?.toString() ?: "Unknown"
                    )
                }
            }
        }
    }
}

@Composable
private fun LoanedBookRow(
    title: String,
    borrowDate: String,
    returnDate: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBC02D)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
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
        }
    }
}

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
            loanedBooks = loanedBooks,
            onBackClick = { navController.popBackStack() } // Handle back button click
        )
    }
}

@Composable
private fun LoanScreenContent(
    loanedBooks: List<Map<String, Any>>,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp)
    ) {
        // Back arrow and title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFFBC02D)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Your Loaned Books",
                color = Color(0xFFFBC02D),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Display loaned books
        if (loanedBooks.isEmpty()) {
            Text(
                text = "You have no books currently loaned.",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(loanedBooks) { book ->
                    LoanedBookRow(
                        title = book["bookTitle"]?.toString() ?: "Unknown Title",
                        borrowDate = (book["borrowDate"] as? Timestamp)?.toDate()?.toString() ?: "Unknown",
                        returnDate = book["returnDate"]?.toString() ?: "Unknown",
                        imageUrl = book["imageUrl"]?.toString() ?: ""
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
    returnDate: String,
    imageUrl: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBC02D)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp) // Increased height for better layout
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book Cover Image
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
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
        }
    }
}

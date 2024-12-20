package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BorrowScreen(navController: NavHostController, bookName: String, userId: String) {
    val firestore = FirebaseFirestore.getInstance()
    var book by remember { mutableStateOf<Map<String, Any>?>(null) }
    val returnDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
        Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 21) }.time
    )

    LaunchedEffect(bookName) {
        firestore.collection("books").whereEqualTo("title", bookName).get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    book = querySnapshot.documents[0].data

                    // Associate book with user
                    firestore.collection("users").document(userId).collection("loans").add(
                        mapOf(
                            "bookTitle" to bookName,
                            "borrowDate" to Date(),
                            "returnDate" to returnDate
                        )
                    )
                }
            }
    }

    book?.let {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF101010))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You borrowed: ${it["title"]?.toString() ?: ""}",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Return by: $returnDate",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Button(
                    onClick = { navController.navigate("searchScreen") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBC02D))
                ) {
                    Text(text = "Continue Browsing", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("homeScreen") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBC02D))
                ) {
                    Text(text = "Return Home", color = Color.White)
                }
            }
        }
    }
}

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
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SearchScreen(navController: NavHostController) {
    val firestore = FirebaseFirestore.getInstance()
    var books by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var genres by remember { mutableStateOf(listOf<String>()) }
    var selectedGenre by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    // Fetch books and genres from Firestore
    LaunchedEffect(Unit) {
        firestore.collection("books").get()
            .addOnSuccessListener { result ->
                books = result.documents.map { doc -> doc.data ?: emptyMap() }
                genres = books.mapNotNull { it["genre"]?.toString() }.distinct()
            }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101010))
                .padding(16.dp)
        ) {
            Text(
                text = "Search Books",
                color = Color(0xFFFBC02D),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Search Bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search...", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
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

            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown for Genre Selection
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = selectedGenre ?: "Select Genre",
                        color = Color.Gray,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    genres.forEach { genre ->
                        DropdownMenuItem(
                            text = { Text(text = genre) },
                            onClick = {
                                selectedGenre = genre
                                expanded = false
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text(text = "All Genres") },
                        onClick = {
                            selectedGenre = null
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display Filtered Books
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(books.filter {
                    val title = it["title"]?.toString() ?: ""
                    val genre = it["genre"]?.toString()
                    title.contains(searchText.text, ignoreCase = true) &&
                            (selectedGenre == null || genre == selectedGenre)
                }) { _, book ->
                    BookRow(
                        title = book["title"]?.toString() ?: "",
                        onDetailsClick = {
                            navController.navigate("bookDetailsScreen/${book["title"]}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BookRow(title: String, onDetailsClick: () -> Unit) {
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
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onDetailsClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "View Book Details", color = Color.White)
            }
        }
    }
}

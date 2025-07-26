// screens/teacher/TeacherHomeScreen.kt
package com.ahmetyusufoguz.stajokulu25.screens.teacher

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.lint.Names.Runtime.LaunchedEffect
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmetyusufoguz.stajokulu25.data.AnnouncementRepository

@Composable
fun TeacherHomeScreen() {
    val announcements = AnnouncementRepository.announcements
    var showDialog by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        AnnouncementRepository.init(context)
    }
    var showConfirmDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Duyurular",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Yeni Duyuru Ekle")
        }

        if (announcements.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Gösterilecek duyuru yok.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
         else {
            LazyColumn {
                items(announcements.toList()) { announcement ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = announcement.title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                IconButton(onClick = { showConfirmDialog = true }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Sil")
                                }
                                if (showConfirmDialog) {
                                    AlertDialog(
                                        onDismissRequest = { showConfirmDialog = false },
                                        title = { Text("Duyuruyu Sil") },
                                        text = { Text("Bu duyuruyu silmek istediğinize emin misiniz?") },
                                        confirmButton = {
                                            TextButton(onClick = {
                                                AnnouncementRepository.remove(
                                                    context,
                                                    announcement.id
                                                )
                                                showConfirmDialog = false
                                            }) {
                                                Text("Evet")
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(onClick = { showConfirmDialog = false }) {
                                                Text("Hayır")
                                            }
                                        }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = announcement.description,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Duyuru Ekle") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Başlık") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Açıklama") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (title.isNotBlank() && description.isNotBlank()) {
                                AnnouncementRepository.add(context, title, description)
                                title = ""
                                description = ""
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Ekle")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("İptal")
                    }
                }
            )
        }
    }
}
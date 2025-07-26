package com.ahmetyusufoguz.stajokulu25.screens.student

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmetyusufoguz.stajokulu25.data.Announcement
import com.ahmetyusufoguz.stajokulu25.data.AnnouncementRepository
import com.ahmetyusufoguz.stajokulu25.data.DummyAnnouncements
import com.ahmetyusufoguz.stajokulu25.data.isAnnouncementRead
import com.ahmetyusufoguz.stajokulu25.data.markAnnouncementAsRead

@Composable
fun StudentHomeScreen() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        AnnouncementRepository.init(context)
    }
    var selectedAnnouncement by remember { mutableStateOf<Announcement?>(null) }
    val announcements = AnnouncementRepository.announcements

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(announcements) { announcement ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            selectedAnnouncement = announcement
                            markAnnouncementAsRead(context, announcement.id)
                            val index = announcements.indexOfFirst { it.id == announcement.id }
                            if (index != -1) {
                                announcements[index] = announcements[index].copy(isRead = true)
                            }
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (announcement.isRead)
                            MaterialTheme.colorScheme.surfaceVariant
                        else
                            MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = announcement.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(16.dp),
                        color = if (announcement.isRead)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }

    selectedAnnouncement?.let { announcement ->
        AlertDialog(
            onDismissRequest = { selectedAnnouncement = null },
            title = { Text(text = announcement.title) },
            text = { Text(text = announcement.description) },
            confirmButton = {
                TextButton(onClick = { selectedAnnouncement = null }) {
                    Text("Kapat")
                }
            }
        )
    }
}
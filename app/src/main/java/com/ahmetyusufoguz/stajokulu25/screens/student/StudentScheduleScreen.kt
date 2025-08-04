package com.ahmetyusufoguz.stajokulu25.screens.student

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmetyusufoguz.stajokulu25.data.Lesson
import com.ahmetyusufoguz.stajokulu25.data.LessonRepository
import com.ahmetyusufoguz.stajokulu25.data.getDateForDay

val days = listOf("Pzt", "Salı", "Çar", "Perş", "Cuma", "Cmt", "Pzr")

@Composable
fun StudentScheduleScreen() {
    val weeks = (1..6).toList()
    var selectedWeek by remember { mutableStateOf(1) }

    Column(modifier = Modifier.fillMaxSize()) {

        // HAFTA SEÇİCİ (sabit butonlar)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            weeks.forEach { week ->
                Button(
                    onClick = { selectedWeek = week },
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedWeek == week)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        "Hafta $week",
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }

        // DERS TABLOSU
        LessonGrid(
            selectedWeek = selectedWeek,
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp)
        )
    }
}

fun timeToMinutes(time: String): Int {
    val (hour, minute) = time.split(":").map { it.toInt() }
    return hour * 60 + minute
}

@Composable
fun LessonGrid(selectedWeek: Int, modifier: Modifier = Modifier) {
    val lessons = LessonRepository.dummyLessons.filter { it.week == selectedWeek }
    val pxPerMin = 1.5f
    val gridStartMin = 9 * 60
    val rowHeight = 90.dp
    val totalWidthDp = ((60 * 11) * pxPerMin).toInt()

    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    var selectedLesson by remember { mutableStateOf<Lesson?>(null) }

    Row(modifier = modifier.fillMaxWidth()) {

        // SOLDA SABİT GÜN + TARİH
        Column(
            modifier = Modifier
                .verticalScroll(verticalScrollState),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            days.forEachIndexed { dayIndex, day ->
                val date = getDateForDay(week = selectedWeek, dayIndex = dayIndex)
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(rowHeight)
                        .border(1.dp, Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("$day\n$date", textAlign = TextAlign.Center)
                }
            }
        }

        // SAĞDA SCROLL EDİLEBİLİR DERSLER
        Column(
            modifier = Modifier
                .verticalScroll(verticalScrollState)
                .horizontalScroll(horizontalScrollState),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            days.forEachIndexed { dayIndex, _ ->
                val lessonsOfDay = lessons.filter { it.day == dayIndex }

                Box(
                    modifier = Modifier
                        .width(totalWidthDp.dp)
                        .height(rowHeight)
                ) {
                    lessonsOfDay.forEach { lesson ->
                        val startMin = timeToMinutes(lesson.begin)
                        val endMin = timeToMinutes(lesson.end)
                        val offsetMin = startMin - gridStartMin
                        val durationMin = endMin - startMin
                        val leftOffset = offsetMin * pxPerMin
                        val boxWidth = durationMin * pxPerMin

                        Box(
                            modifier = Modifier
                                .offset(x = leftOffset.dp)
                                .width(boxWidth.dp)
                                .height(rowHeight)
                                .background(Color(0xFFBBDEFB))
                                .border(1.dp, Color.Black)
                                .clickable { selectedLesson = lesson },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(
                                    lesson.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    lesson.teacherName,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Black
                                )
                                Text(
                                    "${lesson.begin} - ${lesson.end}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        color = Color.LightGray
                    )
                }
            }
        }
    }

    // POP-UP: Ders detayları
    selectedLesson?.let { lesson ->
        AlertDialog(
            onDismissRequest = { selectedLesson = null },
            confirmButton = {
                TextButton(onClick = { selectedLesson = null }) {
                    Text("Kapat")
                }
            },
            title = { Text(lesson.title) },
            text = {
                Column {
                    Text("Öğretmen: ${lesson.teacherName}")
                    Text("Zaman: ${lesson.begin} - ${lesson.end}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Açıklama:\n${lesson.description}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Tanıtım:\n${lesson.teacherDesc}")
                }
            }
        )
    }
}
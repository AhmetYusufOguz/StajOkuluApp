package com.ahmetyusufoguz.stajokulu25.data

import java.util.UUID

data class Lesson(
    val id: UUID,
    val week: Int,               // 1 - 6
    val day: Int,                // 0 = Pazartesi, 6 = Pazar
    val title: String,
    val begin: String,           // "09:00" gibi
    val end: String,             // "11:00" gibi
    val description: String,
    val teacherName: String,
    val teacherDesc: String
)
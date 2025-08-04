package com.ahmetyusufoguz.stajokulu25.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

object LessonRepository {
    val dummyLessons = listOf(
        // Hafta 1
        Lesson(
            id = UUID.fromString("00000000-0000-0000-0000-000000000001"),
            week = 1,
            day = 0,
            title = "Matematik",
            begin = "09:30",
            end = "11:00",
            description = "Fonksiyonlar ve grafikleri.",
            teacherName = "Mehmet Yılmaz",
            teacherDesc = "İTÜ Matematik mezunu, 10 yıllık öğretmen."
        ),
        Lesson(
            id = UUID.fromString("00000000-0000-0000-0000-000000000002"),
            week = 1,
            day = 0,
            title = "Fizik",
            begin = "11:15",
            end = "12:45",
            description = "Newton hareket yasaları.",
            teacherName = "Ayşe Demir",
            teacherDesc = "Boğaziçi Fizik mezunu, 7 yıllık deneyim."
        ),
        Lesson(
            id = UUID.fromString("00000000-0000-0000-0000-000000000003"),
            week = 1,
            day = 1,
            title = "Türkçe",
            begin = "10:00",
            end = "11:30",
            description = "Paragraf analizi.",
            teacherName = "Fatma Şahin",
            teacherDesc = "Edebiyat uzmanı, 8 yıllık deneyim."
        ),

        // Hafta 2
        Lesson(
            id = UUID.fromString("00000000-0000-0000-0000-000000000004"),
            week = 2,
            day = 2,
            title = "Kimya",
            begin = "10:00",
            end = "11:30",
            description = "Asit-baz tepkimeleri.",
            teacherName = "Zeynep Kaya",
            teacherDesc = "ODTÜ Kimya bölümü, 5 yıllık deneyim."
        ),
        Lesson(
            id = UUID.fromString("00000000-0000-0000-0000-000000000005"),
            week = 2,
            day = 2,
            title = "Biyoloji",
            begin = "13:00",
            end = "14:00",
            description = "Hücre zarları ve geçirgenlik.",
            teacherName = "Ali Toprak",
            teacherDesc = "YTÜ Biyoloji bölümü, 4 yıl tecrübe."
        ),

        // Hafta 3
        Lesson(
            id = UUID.fromString("00000000-0000-0000-0000-000000000006"),
            week = 3,
            day = 4,
            title = "Coğrafya",
            begin = "09:00",
            end = "10:30",
            description = "Türkiye'nin iklim tipleri.",
            teacherName = "Ece Güneş",
            teacherDesc = "Marmara Coğrafya mezunu."
        ),
        Lesson(
            id = UUID.fromString("00000000-0000-0000-0000-000000000007"),
            week = 3,
            day = 4,
            title = "Felsefe",
            begin = "10:30",
            end = "11:30",
            description = "Sokrates ve Antik Yunan düşüncesi.",
            teacherName = "Deniz Kar",
            teacherDesc = "Galatasaray Üniversitesi Felsefe bölümü mezunu."
        ),

        // Hafta 4
        Lesson(
            id = UUID.fromString("00000000-0000-0000-0000-000000000008"),
            week = 4,
            day = 3,
            title = "İngilizce",
            begin = "14:00",
            end = "15:30",
            description = "Reading comprehension practice.",
            teacherName = "John Smith",
            teacherDesc = "Native speaker, 6 years experience."
        ),

        // Hafta 5
        Lesson(
            id = UUID.fromString("00000000-0000-0000-0000-000000000009"),
            week = 5,
            day = 6,
            title = "Rehberlik",
            begin = "15:00",
            end = "16:00",
            description = "Motivasyon üzerine seminer.",
            teacherName = "Nilay Karaca",
            teacherDesc = "Psikolojik danışman, 12 yıllık tecrübe."
        ),

        // Hafta 6
        Lesson(
            id = UUID.fromString("00000000-0000-0000-0000-000000000010"),
            week = 6,
            day = 1,
            title = "Kodlama",
            begin = "09:00",
            end = "10:45",
            description = "Scratch ile temel algoritmalar.",
            teacherName = "Ahmet Yusuf Oğuz",
            teacherDesc = "GTÜ Bilgisayar Mühendisliği öğrencisi."
        )
    )
}

fun getDateForDay(week: Int, dayIndex: Int): String {
    val baseDate = LocalDate.of(2025, 6, 30) // 1. haftanın pazartesi
    val daysToAdd = (week - 1) * 7 + dayIndex
    val targetDate = baseDate.plusDays(daysToAdd.toLong())
    return targetDate.format(DateTimeFormatter.ofPattern("d MMM"))
}
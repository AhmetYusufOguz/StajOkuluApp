package com.ahmetyusufoguz.stajokulu25.data

import android.content.Context
import java.util.UUID
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Announcement(
    val id: UUID,
    val userId: UUID,
    val title: String,
    val description: String,
    val isRead: Boolean = false
)

object DummyAnnouncements {
    fun getInitial(): List<Announcement> = listOf(
        Announcement(
            id = UUID.fromString("00000000-0000-0000-0000-000000000001"),
            userId = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            title = "Sistem Girişi Hakkında",
            description = "Sisteme girişler sadece okul maili ile yapılmalıdır."
        ),
        Announcement(
            id = UUID.fromString("00000000-0000-0000-0000-000000000002"),
            userId = UUID.fromString("22222222-2222-2222-2222-222222222222"),
            title = "Piknik Duyurusu",
            description = "Yarınki piknik gezisi için servis saat 9:30'da okuldan kalkacaktır."
        )
    )
}

fun isAnnouncementRead(context: Context, id: UUID): Boolean {
    val prefs = context.getSharedPreferences("read_announcements", Context.MODE_PRIVATE)
    return prefs.getBoolean(id.toString(), false)
}

fun markAnnouncementAsRead(context: Context, id: UUID) {
    val prefs = context.getSharedPreferences("read_announcements", Context.MODE_PRIVATE)
    prefs.edit().putBoolean(id.toString(), true).apply()
}

fun saveAnnouncements(context: Context, list: List<Announcement>) {
    val prefs = context.getSharedPreferences("announcement_data", Context.MODE_PRIVATE)
    val json = Gson().toJson(list)
    prefs.edit().putString("announcement_list", json).apply()
}

fun loadAnnouncements(context: Context): List<Announcement> {
    val prefs = context.getSharedPreferences("announcement_data", Context.MODE_PRIVATE)
    val json = prefs.getString("announcement_list", null)
    return if (json != null) {
        val type = object : TypeToken<List<Announcement>>() {}.type
        Gson().fromJson(json, type)
    } else {
        DummyAnnouncements.getInitial()
    }
}

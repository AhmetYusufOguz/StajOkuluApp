package com.ahmetyusufoguz.stajokulu25.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

object AnnouncementRepository {
    private var initialized = false
    private val _announcements = mutableStateListOf<Announcement>()
    val announcements = _announcements

    fun init(context: Context) {
        val prefs = context.getSharedPreferences("read_announcements", Context.MODE_PRIVATE)
        val storedList = getStoredAnnouncements(context)

        _announcements.clear()
        _announcements.addAll(
            storedList.map {
                it.copy(isRead = prefs.getBoolean(it.id.toString(), false))
            }
        )
    }

    fun add(context: Context, title: String, description: String) {
        val newItem = Announcement(
            id = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            title = title,
            description = description
        )
        _announcements.add(newItem)
        saveAnnouncements(context, _announcements)
    }

    fun remove(context: Context, id: UUID) {
        _announcements.removeIf { it.id == id }
        saveAnnouncements(context, _announcements)
    }

    private fun getStoredAnnouncements(context: Context): List<Announcement> {
        val prefs = context.getSharedPreferences("announcements", Context.MODE_PRIVATE)
        val json = prefs.getString("announcement_list", null) ?: return emptyList()
        val type = object : TypeToken<List<Announcement>>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun saveAnnouncements(context: Context, list: List<Announcement>) {
        val prefs = context.getSharedPreferences("announcements", Context.MODE_PRIVATE)
        val json = Gson().toJson(list)
        prefs.edit().putString("announcement_list", json).apply()
    }
}
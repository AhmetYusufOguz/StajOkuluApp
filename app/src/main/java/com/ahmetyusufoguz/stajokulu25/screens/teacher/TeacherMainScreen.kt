package com.ahmetyusufoguz.stajokulu25.screens.teacher

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherMainScreen(rootNavController: NavController) {
    val localNavController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(1) } // Ana sayfa başlangıç
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val items = listOf(
        NavigationItem("Duyurular", Icons.Default.Announcement, "home"),
        NavigationItem("Ders Programı", Icons.Default.CalendarToday, "schedule"),
        NavigationItem("Harita", Icons.Default.Restaurant, "map")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Hoca Menüsü",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Divider()

                    Spacer(modifier = Modifier.weight(1f))

                    // Çıkış yap butonu
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                        label = { Text("Çıkış Yap") },
                        selected = false,
                        onClick = {
                            // Hoca ve seçim tercihlerini temizle
                            context.getSharedPreferences("teacher_prefs", 0)
                                .edit()
                                .clear()
                                .apply()

                            context.getSharedPreferences("choice_prefs", 0)
                                .edit()
                                .clear()
                                .apply()

                            // Login sayfasına yönlendir
                            rootNavController.navigate("login") {
                                popUpTo("teacher_main") { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Staj Okulu 2025") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                localNavController.navigate(item.route) {
                                    popUpTo(localNavController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = localNavController,
                startDestination = "schedule",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("schedule") {
                    TeacherScheduleScreen()
                }
                composable("home") {
                    TeacherHomeScreen()
                }
                composable("map") {
                    TeacherMapScreen()
                }
            }
        }
    }
}

data class NavigationItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
)
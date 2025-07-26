package com.ahmetyusufoguz.stajokulu25.screens.student

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
fun StudentMainScreen(navController: NavController) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(1) } // Ana sayfa başlangıç
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val items = listOf(
        NavigationItem("Ders Programı", Icons.Default.CalendarToday, "schedule"),
        NavigationItem("Ana Sayfa", Icons.Default.Home, "home"),
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
                        text = "Öğrenci Menüsü",
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
                            // Tüm tercihleri temizle
                            context.getSharedPreferences("choice_prefs", 0)
                                .edit()
                                .clear()
                                .apply()

                            // Login sayfasına yönlendir
                            navController.navigate("login") {
                                popUpTo("student_main") { inclusive = true }
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
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
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
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("schedule") {
                    StudentScheduleScreen()
                }
                composable("home") {
                    StudentHomeScreen()
                }
                composable("map") {
                    StudentMapScreen()
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
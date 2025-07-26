package com.ahmetyusufoguz.stajokulu25

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmetyusufoguz.stajokulu25.screens.LoginScreen
import com.ahmetyusufoguz.stajokulu25.screens.student.StudentMainScreen
import com.ahmetyusufoguz.stajokulu25.screens.teacher.TeacherMainScreen
import com.ahmetyusufoguz.stajokulu25.ui.theme.Stajakulu25Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Stajakulu25Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StajakuluApp()
                }
            }
        }
    }
}

@Composable
fun StajakuluApp() {
    val navController = rememberNavController()

    // TODO: Uygulama yan yatamasın.

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("student_main") {
            StudentMainScreen(navController = navController)
        }
        composable("teacher_main") {
            TeacherMainScreen(navController = navController)
        }
    }
}
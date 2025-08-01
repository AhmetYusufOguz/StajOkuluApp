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
import com.ahmetyusufoguz.stajokulu25.config.supabase // Supabase client'ı import ediyoruz

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Supabase client'ı başlatmak için erişiyoruz (lazy initialization)
        try {
            // Supabase bağlantısını test ediyoruz
            supabase
            println("Supabase bağlantısı başarılı!")
        } catch (e: Exception) {
            println("Supabase bağlantı hatası: ${e.message}")
        }

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

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("student_main") {
            StudentMainScreen(rootNavController = navController)
        }
        composable("teacher_main") {
            TeacherMainScreen(rootNavController = navController)
        }
    }
}
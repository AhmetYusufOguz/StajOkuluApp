package com.ahmetyusufoguz.stajokulu25.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var showTeacherDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("teacher_prefs", 0)
    val isTeacherRemembered = prefs.getBoolean("remember_teacher", false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Staj Okulu 2025",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Button(
            onClick = {
                navController.navigate("student_main") {
                    popUpTo("login") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Öğrenci Girişi",
                fontSize = 18.sp
            )
        }

        Button(
            onClick = {
                if (isTeacherRemembered) {
                    navController.navigate("teacher_main") {
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    showTeacherDialog = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Hoca Girişi",
                fontSize = 18.sp
            )
        }
    }

    if (showTeacherDialog) {
        TeacherLoginDialog(
            onDismiss = { showTeacherDialog = false },
            onSuccess = {
                showTeacherDialog = false
                navController.navigate("teacher_main") {
                    popUpTo("login") { inclusive = true }
                }
            }
        )
    }
}

@Composable
fun TeacherLoginDialog(
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val correctPassword = "hoca123" // Sabit şifre

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hoca Girişi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        showError = false
                    },
                    label = { Text("Şifre") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Şifreyi gizle" else "Şifreyi göster"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (password == correctPassword) {
                                if (rememberMe) {
                                    context.getSharedPreferences("teacher_prefs", 0)
                                        .edit()
                                        .putBoolean("remember_teacher", true)
                                        .apply()
                                }
                                onSuccess()
                            } else {
                                showError = true
                            }
                        }
                    ),
                    isError = showError,
                    modifier = Modifier.fillMaxWidth()
                )

                if (showError) {
                    Text(
                        text = "Yanlış şifre!",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it }
                    )
                    Text(
                        text = "Beni hatırla",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("İptal")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (password == correctPassword) {
                                if (rememberMe) {
                                    context.getSharedPreferences("teacher_prefs", 0)
                                        .edit()
                                        .putBoolean("remember_teacher", true)
                                        .apply()
                                }
                                onSuccess()
                            } else {
                                showError = true
                            }
                        }
                    ) {
                        Text("Giriş")
                    }
                }
            }
        }
    }
}
package com.example.formulario_mascotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.formulario_mascotas.ui.theme.Formulario_MascotasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Formulario_MascotasTheme {
                val navController = rememberNavController()
                val listaMascotas = remember { mutableStateOf(mutableListOf<Mascota>()) }

                NavHost(navController = navController, startDestination = "Screen_A") {
                    composable("Screen_A") {
                        Screen_A(navController, listaMascotas)
                    }
                    composable("Screen_B") {
                        Screen_B(listaMascotas, navController)
                    }
                }
            }
        }
    }
}

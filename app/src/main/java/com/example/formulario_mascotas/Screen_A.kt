package com.example.formulario_mascotas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Screen_A(
    navController: NavHostController,
    listaMascotas: MutableState<MutableList<Mascota>>
) {
    var nombre by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var tamano by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE0B2))
            .padding(15.dp)
    ) {
        Text(
            text = "🐕 Registra a tu mascota",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color(0xFFFF7043),
            modifier = Modifier
                .padding(vertical = 15.dp)
                .align(Alignment.CenterHorizontally)
        )

        @Composable
        fun textFieldStyle(label: String, value: String, onChange: (String) -> Unit) =
            OutlinedTextField(
                value = value,
                onValueChange = onChange,
                label = {
                    Text("$label 🐾", color = Color.Black, fontWeight = FontWeight.Bold)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black
                ),
                textStyle = TextStyle(color = Color.Black)
            )

        textFieldStyle("Nombre", nombre) { nombre = it }
        textFieldStyle("Raza", raza) { raza = it }
        textFieldStyle("Tamaño", tamano) { tamano = it }
        textFieldStyle("Edad", edad) { edad = it }
        textFieldStyle("URL de la foto", foto) { foto = it }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                if (nombre.isNotEmpty() && raza.isNotEmpty() && tamano.isNotEmpty() && edad.isNotEmpty() && foto.isNotEmpty()) {
                    val nuevaMascota = Mascota(nombre, raza, tamano, edad, foto)
                    listaMascotas.value.add(nuevaMascota)
                    navController.navigate("Screen_B")
                }else{ }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043))
        ) {
            Text("Registrar 🐶", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}
package com.example.formulario_mascotas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage

@Composable
fun Screen_B(
    listaMascotas: MutableState<MutableList<Mascota>>,
    navController: NavHostController
) {
    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }
    var indexSeleccionado by remember { mutableStateOf(-1) }
    var mostrarConfirmacionEliminar by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF3E0))
            .padding(15.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(listaMascotas.value) { index, mascota ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                mascotaSeleccionada = mascota
                                indexSeleccionado = index
                            },
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCCBC))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = mascota.foto,
                                contentDescription = "Foto de ${mascota.nombre}",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(13.dp))
                                    .background(Color.White)
                            )

                            Spacer(modifier = Modifier.width(15.dp))

                            Column {
                                Text("\uD83D\uDC36 ${mascota.nombre}", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
                                Text("Raza: ${mascota.raza}", color = Color.Black)
                                Text("Tamaño: ${mascota.tamano}", color = Color.Black)
                                Text("Edad: ${mascota.edad}", color = Color.Black)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043))
            ) {
                Text("Volver a registrar \uD83D\uDC3E", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        mascotaSeleccionada?.let { mascota ->
            var nombre by remember { mutableStateOf(mascota.nombre) }
            var raza by remember { mutableStateOf(mascota.raza) }
            var tamano by remember { mutableStateOf(mascota.tamano) }
            var edad by remember { mutableStateOf(mascota.edad) }
            var foto by remember { mutableStateOf(mascota.foto) }

            AlertDialog(
                onDismissRequest = {
                    mascotaSeleccionada = null
                    indexSeleccionado = -1
                },
                title = { Text("Editar Mascota \uD83D\uDC3E", fontWeight = FontWeight.Bold, color = Color.Black) },
                text = {
                    Column(modifier = Modifier.padding(9.dp)) {
                        AsyncImage(
                            model = foto,
                            contentDescription = "Foto de la mascota",
                            modifier = Modifier
                                .size(150.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(15.dp))
                        )

                        Spacer(modifier = Modifier.height(9.dp))

                        @Composable
                        fun campo(label: String, valor: String, onChange: (String) -> Unit) {
                            OutlinedTextField(
                                value = valor,
                                onValueChange = onChange,
                                label = { Text(label, color = Color.Black) },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Gray,
                                    cursorColor = Color.Black
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }

                        campo("Nombre", nombre) { nombre = it }
                        campo("Raza", raza) { raza = it }
                        campo("Tamaño", tamano) { tamano = it }
                        campo("Edad", edad) { edad = it }
                        campo("URL Foto", foto) { foto = it }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val lista = listaMascotas.value.toMutableList()
                        lista[indexSeleccionado] = Mascota(nombre, raza, tamano, edad, foto)
                        listaMascotas.value = lista
                        mascotaSeleccionada = null
                        indexSeleccionado = -1
                    }) {
                        Text("Guardar", color = Color.White)
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        mostrarConfirmacionEliminar = true
                    }) {
                        Text("Eliminar", color = Color.White)
                    }
                },
                containerColor = Color(0xFFFFE0B2),
                shape = RoundedCornerShape(15.dp)
            )
        }

        if (mostrarConfirmacionEliminar) {
            AlertDialog(
                onDismissRequest = { mostrarConfirmacionEliminar = false },
                text= { Text("¿Eliminar mascota?", fontWeight = FontWeight.Bold, color = Color.Black) },
                confirmButton = {
                    Button(onClick = {
                        if (indexSeleccionado in listaMascotas.value.indices) {
                            val lista = listaMascotas.value.toMutableList()
                            lista.removeAt(indexSeleccionado)
                            listaMascotas.value = lista
                        }
                        mostrarConfirmacionEliminar = false
                        mascotaSeleccionada = null
                        indexSeleccionado = -1
                    }) {
                        Text("eliminar", color = Color.White)
                    }
                },
                dismissButton = {
                    Button(onClick = { mostrarConfirmacionEliminar = false }) {
                        Text("Cancelar", color = Color.White)
                    }
                },
                containerColor = Color(0xFFFFCCBC),
                shape = RoundedCornerShape(15.dp)
            )
        }
    }
}
package com.example.basiclabproject.feature.courseScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.basiclabproject.models.AspectosBasicosModel

@Composable
fun CourseScreen(
    navController: NavController,
    cursoId: String
) {
    val viewModel = hiltViewModel<CourseScreenViewModel>()
    val cContent by viewModel.cContent.observeAsState()

    LaunchedEffect(cursoId) {
        viewModel.fetchData(cursoId)
    }

    cContent?.let {
        CourseItem(
            it.copy(
                id = cContent?.id ?: "",
                titulo = cContent?.titulo ?: "",
                dificultad = cContent?.dificultad ?: "",
                descripcion = cContent?.descripcion ?: "",
                topicos = cContent?.topicos ?: emptyList(),
                lecciones = cContent?.lecciones?.copy(
                    ejemplos = cContent?.lecciones?.ejemplos ?: "",
                    leccion1 = cContent?.lecciones?.leccion1 ?: "",
                    leccion2 = cContent?.lecciones?.leccion2 ?: ""
                )
            )
        )
    }

//    Text(text = "Curso ID: $cursoId")
}

@Composable
fun CourseItem(course: AspectosBasicosModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Dificultad: ${course.dificultad}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Descripción: ${course.descripcion}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Contenido: ${course.lecciones?.ejemplos}",
                style = MaterialTheme.typography.bodyLarge
            )

            Row {
                course.topicos.forEach { topico ->
                    Text(
                        text = "$topico  ",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }


        }
    }
}
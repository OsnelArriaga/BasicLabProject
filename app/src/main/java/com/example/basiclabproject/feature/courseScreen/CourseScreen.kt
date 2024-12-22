package com.example.basiclabproject.feature.courseScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.basiclabproject.models.CoursesInfo

@Composable
fun CourseScreen(
    navController: NavController,
    cursoId: String,
    viewModel: CourseScreenViewModel = hiltViewModel()
) {
//    val viewModel = hiltViewModel<CourseScreenViewModel>()

//    val itemName by viewModel::coursesInfo
    val coursesInfo by viewModel::xxx

    LaunchedEffect(cursoId) {
        viewModel.fetchCourses(cursoId)
    }

    Text(
        text = "Nombre del ítem: ${coursesInfo.value}",
        style = MaterialTheme.typography.titleLarge
    )

//    LazyColumn(modifier = Modifier
//        .fillMaxSize()
//        .padding(16.dp)) {
//        items(coursesInfo) { course ->
//            CourseItem(course)
////            Text(text = item.dificultad)
//        }
//    }

//    Text(text = "Curso ID: $cursoId")


}

@Composable
fun CourseItem(course: CoursesInfo) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
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
                text = "Lecciones: ${course.lecciones}",
                style = MaterialTheme.typography.bodyLarge
            )
//            Text(
//                text = "Tópicos: ${course.topicos.joinToString(", ")}",
//                style = MaterialTheme.typography.bodyLarge
//            )
        }
    }
}
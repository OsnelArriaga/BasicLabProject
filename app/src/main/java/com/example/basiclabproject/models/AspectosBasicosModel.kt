package com.example.basiclabproject.models

data class CardInfo(
    val id: String = "",
    val titulo: String = "",
    val dificultad: String = ""
)

data class CoursesInfo(
    val id: String = "",
    val titulo: String = "",
    val dificultad: String = "",
    val descripcion: String = "",
    val contenido: String = "",
    val topicos: List<String> = emptyList(),
    val lecciones: String = ""
)
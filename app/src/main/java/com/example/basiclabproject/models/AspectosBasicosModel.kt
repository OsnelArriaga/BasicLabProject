package com.example.basiclabproject.models


data class Leccion(
    val ejemplos: String = "",
    val leccion1: String = "",
    val leccion2: String = ""

)

data class CardInfo(
    val id: String = "",
    val titulo: String = "",
    val dificultad: String = "",
    val descripcion: String = "",
    val topicos: List<String> = emptyList(),
    val lecciones: Leccion? = null
)


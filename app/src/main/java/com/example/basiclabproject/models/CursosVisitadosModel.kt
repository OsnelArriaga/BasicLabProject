package com.example.basiclabproject.models

data class CursosVisitadosModel(
    val id:String = "",
    val userId: String = "",
    val courseId: String = "",
    val titulo: String = "",
    val createAt: Long = System.currentTimeMillis(),
    val topicos: List<String> = emptyList(),
    val dificultad: String = ""
)
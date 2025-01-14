package com.example.basiclabproject.models

data class AspectosBasicosModel(
    val id: String = "",
    val titulo: String = "",
    val dificultad: String = "",
    val descripcion: String = "",
    val topicos: List<String> = emptyList(),
    val lecciones: LeccionesBasicas? = null,
    val imagen: String = ""
)

data class LeccionesBasicas(
    val ejemplos: String = "",
    val leccion1: String = "",
    val leccion2: String = ""

)

data class FundamentosModel(
    val id: String = "",
    val titulo: String = "",
    val dificultad: String = "",
    val descripcion: String = "",
    val topicos: List<String> = emptyList(),
    val lecciones: LeccionesFundamentos? = null,
    val imagen: String = ""
)

data class LeccionesFundamentos(
    val ejemplos: String = "",
    val leccion1: String = "",
    val leccion2: String = ""

)


data class HerramientasModel(
    val id: String = "",
    val titulo: String = "",
    val dificultad: String = "",
    val descripcion: String = "",
    val topicos: List<String> = emptyList(),
    val lecciones: LeccionesHerramientas? = null,
    val imagen: String = ""
)

data class LeccionesHerramientas(
    val ejemplos: String = "",
    val leccion1: String = "",
    val leccion2: String = ""

)


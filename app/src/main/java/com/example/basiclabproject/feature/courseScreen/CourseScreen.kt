package com.example.basiclabproject.feature.courseScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.outlined.BookOnline
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.basiclabproject.feature.courseScreen.features.YoutubeLeccionHandler
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
            Modifier.fillMaxSize(),
            navController = navController,
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
                ),
                video = cContent?.video ?: ""
            ),
        )
    }
}

@Composable
fun CourseItem(
    modifier: Modifier = Modifier,
    navController: NavController,
    course: AspectosBasicosModel
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(
                scrollState,
                reverseScrolling = false
            )
    ) {
        Box(Modifier.zIndex(1f)) {

            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .zIndex(2f)
            ) {
                BackButton(onBackTouch = { navController.popBackStack() })
            }

            AsyncImage(
                model = course.imagen,
                contentDescription = "Imagen de prueba",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = course.titulo,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )

                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .size(90.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Mood,
                            contentDescription = "Happy",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(28.dp)
                        )

                        Text(
                            text = course.dificultad,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )

                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Category,
                    contentDescription = "categorias",
                    tint = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = "Categorías",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                course.topicos.forEach { topico ->
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(
                            text = topico,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(10.dp)

                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "info",
                    tint = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = course.descripcion,
                style = MaterialTheme.typography.bodyLarge
            )

            Divider(
                color = MaterialTheme.colorScheme.onSurface,
                thickness = 2.dp,
                modifier = Modifier.padding(10.dp)
            )

            SeccionContenido(course)
        }
    }
}

@Composable
fun SeccionContenido(
    course: AspectosBasicosModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(1200.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                contentDescription = "dificultad:",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(42.dp)
            )

            Text(
                text = "Contenido de la lección",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
            )
        }

        YoutubeLeccionHandler(course.video)

        ContenidoLeccion(course)
    }
}

@Composable
fun ContenidoLeccion(
    cardInfo: AspectosBasicosModel,
) {
    val cardInfoLengt = listOf(
        cardInfo.lecciones?.leccion1 ?: "",
        cardInfo.lecciones?.leccion2 ?: "",
        cardInfo.lecciones?.ejemplos ?: "",
    )

    val pager = rememberPagerState(
        initialPage = 0,
        pageCount = { cardInfoLengt.size },
        initialPageOffsetFraction = 0f,
    )

    HorizontalPager(
        state = pager,
        modifier = Modifier
            .padding(10.dp, 0.dp)
            .height(500.dp) // Altura del pager
    ) { page ->

        val scrollState = rememberScrollState()

        Column {
            // Contenido desplazable
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                Text(
                    "    " + cardInfoLengt[page],
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

    }

    // Indicadores de página
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp, top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pager.pageCount) { iteration ->
            val color =
                if (pager.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.White
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}

@Composable
fun ScrollableContentWithProgressBar() {
    // Estado de desplazamiento
    val scrollState = rememberScrollState()

    // Contenido largo para demostrar el desplazamiento
    val itemCount = 50
    val totalHeight = itemCount * 50 // Altura estimada de cada elemento (ajusta según tu contenido)

    // Calcular el progreso de desplazamiento
    val progress = scrollState.value / (totalHeight - 300f) // Ajusta 300f según la altura visible

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra de progreso
        LinearProgressIndicator(
            progress = progress.coerceIn(0f, 1f), // Asegúrate de que el progreso esté entre 0 y 1
            modifier = Modifier.fillMaxWidth()
        )

        // Contenido desplazable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState) // Habilitar el desplazamiento vertical
        ) {
            // Generar contenido largo para demostrar el desplazamiento
            repeat(itemCount) { index ->
                Text("Elemento ${index + 1}", modifier = Modifier.padding(8.dp))
            }
        }
    }
}

//Boton regresar
@Composable
fun BackButton(onBackTouch: () -> Unit) {
    IconButton(
        onClick = onBackTouch,
        modifier = Modifier
            .size(40.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = Color.Transparent
        )
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back Button",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}
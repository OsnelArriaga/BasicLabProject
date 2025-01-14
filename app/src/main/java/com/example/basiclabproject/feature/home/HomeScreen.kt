package com.example.basiclabproject.feature.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.basiclabproject.models.AspectosBasicosModel
import com.example.basiclabproject.models.FundamentosModel
import com.example.basiclabproject.models.HerramientasModel
import com.example.basiclabproject.navigation.Screens
import com.example.basiclabproject.ui.stylepresets.buttons.buttonSecondaryStyle
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController
) {
    //inicializando el viewModel
    val viewModel = hiltViewModel<HomeViewModel>()

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        ) {

        Column(
            modifier = Modifier
                .verticalScroll(
                    scrollState,
                    reverseScrolling = false
                )
        ) {

            Dashboard(navController, viewModel)

            LeccionesVisitadas(viewModel, navController)

            AspectosBasicos(navController)

            FundamentosSeccion(navController)

            HerramientasSeccion(navController)
        }
    }

}

@Composable
fun Dashboard(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val currentUser = FirebaseAuth.getInstance().currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.spacedBy(
            16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Es bueno verte!",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,

            )

        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = currentUser?.displayName.toString().trim().take(1).uppercase(),
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                style = TextStyle(fontSize = 24.sp),
                textAlign = TextAlign.Center
            )

        }

        Text(
            text = currentUser?.displayName.toString(),
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        LogoutButton(onLogoutClick = { viewModel.logoutUser(navController) })

        Text(
            text = "Resumen de tu actividad",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LeccionesVisitadas(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val data by viewModel.dataFromFirebase.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.leerDatosDelPadre { data ->
            data.keys.forEach { key ->
                Log.w("Firebasewww", "Clave: $key")
            }
        }

    }

    val pager = rememberPagerState(
        initialPage = 0,
        pageCount = { data.size },
        initialPageOffsetFraction = 0f
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(
            5.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = "Lecciones recientes",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(16.dp, 0.dp)
        )

        HorizontalPager(
            state = pager,
        ) { page ->

            LeccionesVisitadasItem(
                viewModel,
                data.values.elementAt(page).toString().split("=", ",").elementAt(2),
                data.keys.elementAt(page),
                data.values.elementAtOrNull(page).toString().split("=", ",", "}}").elementAt(15),
            ) {

                //redireccionar al curso
                val valueList = data.keys.elementAt(page)

                navController.navigate(
                    Screens.CourseScreen.passId(valueList)
                )
            }
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pager.pageCount) { iteration ->
                val color =
                    if (pager.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
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
}

@Composable
fun LeccionesVisitadasItem(
    viewModel: HomeViewModel = hiltViewModel(),
    curso: String,
    eLeccionVisitada: String,
    dificultad: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 15.dp)
            .size(150.dp, 120.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(16.dp))
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = curso,
                modifier = Modifier
                    .padding(12.dp)
                    .width(280.dp)
                    .align(Alignment.CenterStart),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )

            IconButton(
                onClick = {
                    viewModel.borrarLeccionEspecifica(
                        eLeccionVisitada
                    )
                },
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.TopEnd),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Borrar lección",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Row(
            modifier = Modifier.padding(12.dp, 0.dp)
        ) {
            Text(
                text = "Dificultad: ",
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = dificultad,
                style = MaterialTheme.typography.titleMedium,
            )
        }

    }
}

/// SECCION ASPECTOS BASICOS

@Composable
fun AspectosBasicos(navController: NavController) {
    Box() {

        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Text(
                text = "Aspectos Escenciales",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(10.dp, 0.dp)
            )

            Text(
                text = "Conceptos escenciales para comenzar \n" + "en el mundo de la programación.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(10.dp, 0.dp)
            )

            UserListScreen(navController = navController)
        }

    }
}

@Composable
fun UserListScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController
) {

    val cardInfo by viewModel::aspectosBasicosModel
    val isLoading by viewModel::isLoading

    val itemSpacing = 2.dp
    val pager = rememberPagerState(
        initialPage = 0,
        pageCount = { cardInfo.size} ,
        initialPageOffsetFraction = 0f,
    )

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary)
    } else {

        Column(){
            HorizontalPager(
                state = pager,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pager,
                    pagerSnapDistance = PagerSnapDistance.atMost(0)
                ),
                contentPadding = PaddingValues(0.dp,0.dp,80.dp,0.dp),
                pageSpacing = itemSpacing
            ) { page ->

                ChannelItem(cardInfo[page]) {
                    viewModel.guardarCurso(
                        cardInfo[page].id,
                        cardInfo[page].titulo,
                        cardInfo[page].topicos,
                        cardInfo[page].dificultad
                    )

                    navController.navigate(
                        Screens.CourseScreen.passId(cardInfo[page].id)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pager.pageCount) { iteration ->
                    val color = if (pager.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
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
    }
}

@Composable
fun ChannelItem(
    leccion: AspectosBasicosModel,
    onClick: () -> Unit,) {

    Column(
        modifier = Modifier
            .padding(10.dp, 10.dp)
            .height(310.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.Start
    ) {

        AsyncImage(
            model = leccion.imagen,
            contentDescription = "Imagen de prueba",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(150.dp)
        )

        Text(
            text = leccion.titulo,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )

        Text(
            text = "Dificultad: ${leccion.dificultad}",
            modifier = Modifier.padding(15.dp, 5.dp),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        )

        Text(
            text = "Tópicos: ${leccion.topicos.joinToString(", ")}",
            modifier = Modifier.padding(15.dp, 5.dp),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        )

    }
}

/// SECCION DE FUNDAMENTOS
@Composable
fun FundamentosSeccion(navController: NavController) {
    Box() {
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Text(
                text = "Aspectos Escenciales",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(10.dp, 0.dp)
            )

            Text(
                text = "Conceptos escenciales para comenzar \n" + "en el mundo de la programación.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(10.dp, 0.dp)
            )

            FundamentosContent(navController = navController)
        }

    }
}

@Composable
fun FundamentosContent(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController
) {

    val cardInfo by viewModel::fundamentosModels
    val isLoading by viewModel::isLoading

    val itemSpacing = 2.dp
    val pager = rememberPagerState(
        initialPage = 0,
        pageCount = { cardInfo.size} ,
        initialPageOffsetFraction = 0f,
    )

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary)
    } else {

        Column(){
            HorizontalPager(
                state = pager,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pager,
                    pagerSnapDistance = PagerSnapDistance.atMost(0)
                ),
                contentPadding = PaddingValues(0.dp,0.dp,80.dp,0.dp),
                pageSpacing = itemSpacing
            ) { page ->

                FundamentosContentItem(cardInfo[page]) {
                    viewModel.guardarCurso(
                        cardInfo[page].id,
                        cardInfo[page].titulo,
                        cardInfo[page].topicos,
                        cardInfo[page].dificultad
                    )

                    navController.navigate(
                        Screens.CourseScreen.passId(cardInfo[page].id)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pager.pageCount) { iteration ->
                    val color = if (pager.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
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
    }
}

@Composable
fun FundamentosContentItem(
    leccion: FundamentosModel,
    onClick: () -> Unit,) {

    Column(
        modifier = Modifier
            .padding(10.dp, 10.dp)
            .height(310.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.Start
    ) {

        AsyncImage(
            model = leccion.imagen,
            contentDescription = "Imagen de prueba",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(150.dp)
        )

        Text(
            text = leccion.titulo,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )

        Text(
            text = "Dificultad: ${leccion.dificultad}",
            modifier = Modifier.padding(15.dp, 5.dp),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        )

        Text(
            text = "Tópicos: ${leccion.topicos.joinToString(", ")}",
            modifier = Modifier.padding(15.dp, 5.dp),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        )

    }
}

/// HERRAMIENTAS

@Composable
fun HerramientasSeccion(navController: NavController) {
    Box() {

        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Text(
                text = "Aspectos Escenciales",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(10.dp, 0.dp)
            )

            Text(
                text = "Conceptos escenciales para comenzar \n" + "en el mundo de la programación.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(10.dp, 0.dp)
            )

            HerramientasContent(navController = navController)
        }

    }
}

@Composable
fun HerramientasContent(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController
) {

    val cardInfo by viewModel::herrramientasModels
    val isLoading by viewModel::isLoading

    val itemSpacing = 2.dp
    val pager = rememberPagerState(
        initialPage = 0,
        pageCount = { cardInfo.size} ,
        initialPageOffsetFraction = 0f,
    )

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary)
    } else {

        Column(){
            HorizontalPager(
                state = pager,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pager,
                    pagerSnapDistance = PagerSnapDistance.atMost(0)
                ),
                contentPadding = PaddingValues(0.dp,0.dp,80.dp,0.dp),
                pageSpacing = itemSpacing
            ) { page ->

                HerramientasContentItem(cardInfo[page]) {
                    viewModel.guardarCurso(
                        cardInfo[page].id,
                        cardInfo[page].titulo,
                        cardInfo[page].topicos,
                        cardInfo[page].dificultad
                    )

                    navController.navigate(
                        Screens.CourseScreen.passId(cardInfo[page].id)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pager.pageCount) { iteration ->
                    val color = if (pager.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
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
    }
}

@Composable
fun HerramientasContentItem(
    leccion: HerramientasModel,
    onClick: () -> Unit,) {

    Column(
        modifier = Modifier
            .padding(10.dp, 10.dp)
            .height(310.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.Start
    ) {

        AsyncImage(
            model = leccion.imagen,
            contentDescription = "Imagen de prueba",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(150.dp)
        )

        Text(
            text = leccion.titulo,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )

        Text(
            text = "Dificultad: ${leccion.dificultad}",
            modifier = Modifier.padding(15.dp, 5.dp),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        )

        Text(
            text = "Tópicos: ${leccion.topicos.joinToString(", ")}",
            modifier = Modifier.padding(15.dp, 5.dp),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        )

    }
}




@Composable
fun LogoutButton(onLogoutClick: () -> Unit) {

    //Register Button
    Button(
        onClick = onLogoutClick,
        colors = buttonSecondaryStyle()
    ) {

        Text(text = "Cerrar sesión", Modifier.padding(vertical = 8.dp), fontSize = 16.sp)
    }
}
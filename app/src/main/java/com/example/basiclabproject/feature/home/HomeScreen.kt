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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.basiclabproject.models.CardInfo
import com.example.basiclabproject.models.CursosVisitadosModel
import com.example.basiclabproject.navigation.Screens
import com.example.basiclabproject.ui.theme.DarkGreen
import com.example.basiclabproject.ui.theme.Green60
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController
) {

    //inicializando el viewModel
    val viewModel = hiltViewModel<HomeViewModel>()

    var cursos by remember { mutableStateOf(listOf<CardInfo>()) }

    var selectedCoursesId by remember { mutableStateOf("") }

    //inicializar la variable para almacenar el contenido a mostrar de la BD
//    val channels = viewModel.channels.collectAsState()
    //Agregar canal
//    val addChannel = remember {
//        mutableStateOf(false)
//    }

    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        topBar = {
            TopAppBar(
                modifier = Modifier.padding(vertical = 12.dp),
                title = {

                    val currentUser = FirebaseAuth.getInstance().currentUser

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(DarkGreen)
                        ) {
                            Text(
                                text = "U",
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.White,
                                style = TextStyle(fontSize = 24.sp),
                                textAlign = TextAlign.Center
                            )

                        }

                        Text(
                            text = currentUser?.displayName.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )

//                        Text(
//                            text = "Nivel: Intermedio",
//                            fontSize = 18.sp,
//                            style = MaterialTheme.typography.titleLarge
//                        )

                        LogoutButton(onLogoutClick = { viewModel.logoutUser(navController) })
                    }
                }
            )
        }

    ) { innerpadding ->

        Column() {

            CoursesHistory(innerpadding, viewModel, navController)

            AspectosBasicos(navController)
        }
    }

}

@Composable
fun CoursesHistory(
    innerpadding: PaddingValues,
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
        modifier = Modifier
            .padding(innerpadding)
    ) {
        Text(
            text = "Lecciones visitadas",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(16.dp, 0.dp)
        )


        HorizontalPager(
            state = pager,
        ) { page ->

            ChannelItem2(
                viewModel,
                data.values.elementAt(page).toString().split("-", "=", ",").elementAt(3),
                data.keys.elementAt(page)
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
                val color = if (pager.currentPage == iteration) Green60 else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(12.dp)
                )
            }
        }
    }

}

@Composable
fun ChannelItem2(
    viewModel: HomeViewModel = hiltViewModel(),
    curso: String,
    eLeccionVisitada: String,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 15.dp)
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(DarkGreen)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {

//        Text(
//            text = curso.keys.,
//            modifier = Modifier.padding(16.dp),
//            color = Color.White,
//            fontWeight = FontWeight.Medium,
//            fontSize = 24.sp
//        )

        Text(
            text = curso,
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )

        BorrarLeccionButton(onEliminarCursoVisitado = {
            viewModel.borrarLeccionEspecifica(
                eLeccionVisitada
            )
        })

    }
}

@Composable
fun BorrarLeccionButton(
    onEliminarCursoVisitado: () -> Unit
) {

    Button(
        onClick = {
            onEliminarCursoVisitado()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = Green60,
            contentColor = Color.White,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Red
        )
    ) {

        Text(text = "Borrar leccion", Modifier.padding(vertical = 8.dp), fontSize = 16.sp)

    }
}


@Composable
fun AspectosBasicos(navController: NavController) {
//    LaunchedEffect(pager.currentPage) {
//        viewModel.listenerForCourses(
//            Firebase.auth.currentUser?.uid ?: "",
//            cardInfo[pager.currentPage].id
//        )
//    }

    Box() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Aspectos",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "escenciales",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.size(20.dp))

            Text(text = "Detalles de la categoria", style = MaterialTheme.typography.titleLarge)

            UserListScreen(navController = navController)
        }

    }
}

@Composable
fun UserListScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController
) {

    val cardInfo by viewModel::cardInfo
    val isLoading by viewModel::isLoading

    val pager = rememberPagerState(
        initialPage = 0,
        pageCount = { cardInfo.size },
        initialPageOffsetFraction = 0f
    )

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    } else {

        Column {

            HorizontalPager(
                state = pager,
            ) { page ->

                ChannelItem(cardInfo[page]) {
                    viewModel.guardarCurso(
                        cardInfo[page].id,
                        cardInfo[page].titulo
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
                    val color = if (pager.currentPage == iteration) Green60 else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(12.dp)
                    )
                }
            }
        }

    }

}

@Composable
fun ChannelItem(curso: CardInfo, onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 15.dp)
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(DarkGreen)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {

        Text(
            text = curso.titulo,
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )

        Text(
            text = "Dificultad: ${curso.dificultad}",
            modifier = Modifier.padding(16.dp, 20.dp),
            color = Color.White,
            fontSize = 18.sp
        )

    }
}

@Composable
fun LogoutButton(onLogoutClick: () -> Unit) {

    //Register Button
    Button(
        onClick = onLogoutClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Green60,
            contentColor = Color.White,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Red
        )
    ) {

        Text(text = "Cerrar sesión", Modifier.padding(vertical = 8.dp), fontSize = 16.sp)
    }
}
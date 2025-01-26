package com.example.basiclabproject.feature.home.features

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhonelinkRing
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.SlowMotionVideo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun YoutubeVideoHandler() {
    val viewModel = hiltViewModel<YoutubeHandlerViewModel>()

    //val cardInfo by viewModel::videoUrl
    val cardInfo by viewModel::videoUrl
    val isLoading by viewModel::isLoading

    val itemSpacing = 20.dp
    val pager = rememberPagerState(
        initialPage = 0,
        pageCount = { cardInfo.size },
        initialPageOffsetFraction = 0f,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 15.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.SlowMotionVideo,
            contentDescription = "dificultad:",
            tint = Color.Red,
            modifier = Modifier.size(32.dp)
        )

        Text(
            text = "@rafaelmontenegrob",
            color = Color.White,
            )

        val context = LocalContext.current

        Button(
            onClick = {
                val url = "https://www.youtube.com/@rafaelmontenegrob"

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }

                context.startActivity(intent)
            },
            colors = ButtonColors(
                containerColor = Color.Red,
                contentColor = Color.White,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
            modifier = Modifier.wrapContentSize()
        ) {
            Text("Visitar canal")
        }
    }


    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )
    } else {
        HorizontalPager(
            state = pager,
            flingBehavior = PagerDefaults.flingBehavior(
                state = pager,
                pagerSnapDistance = PagerSnapDistance.atMost(0)
            ),
            contentPadding = PaddingValues(10.dp, 0.dp, 0.dp, 0.dp),
            pageSpacing = itemSpacing,
            modifier = Modifier.padding(10.dp, 0.dp)
        ) { page ->

            YoutubleHandlerSliderItem(
                cardInfo[page].link
            )
        }

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pager.pageCount) { iteration ->
                val color =
                    if (pager.currentPage == iteration) Color.Red else Color.White
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
fun YoutubleHandlerSliderItem(
    links: String,
) {
    val webViewHandler = WebView(LocalContext.current).apply {
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        webViewClient = WebViewClient()

        // Establecer un OnFocusChangeListener
        setOnFocusChangeListener { _, hasFocus ->
            stopLoading()
        }
    }

    fun getHTMLData(videoId: String): String {
        return """
        <html>
            <body style="margin:0px;padding:0px;">

            <iframe id="ytplayer" type="text/html"
                width="360"
                height="250"
                src="https://www.youtube.com/embed/$videoId"
                frameborder="0">
            </iframe>

               <div id="ytplayer"></div>
                <script>
                  // Load the IFrame Player API code asynchronously.
                  var tag = document.createElement('script');
                  tag.src = "https://www.youtube.com/player_api";
                  var firstScriptTag = document.getElementsByTagName('script')[0];
                  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

                  // Replace the 'ytplayer' element with an <iframe> and
                  // YouTube player after the API code downloads.
                  var player;
                </script>
                <script src="https://www.youtube.com/iframe_api"></script>
            </body>
        </html>
    """.trimIndent()
    }

    val htmlData = getHTMLData(links)

    Log.w("FirebaseYTHandlerLinks", "Clave: $links")

    AndroidView(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .width(360.dp)
            .height(250.dp),
        onRelease = { webViewHandler.destroy() },
        onReset = { webViewHandler.clearHistory() },
        factory = { webViewHandler }) { view ->

        view.loadDataWithBaseURL(
            "https://www.youtube.com/embed/",
            htmlData,
            "text/html",
            "UTF-8",
            null
        )


    }
}

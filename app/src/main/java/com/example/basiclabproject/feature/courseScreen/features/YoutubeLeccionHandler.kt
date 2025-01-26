package com.example.basiclabproject.feature.courseScreen.features

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun YoutubeLeccionHandler(
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
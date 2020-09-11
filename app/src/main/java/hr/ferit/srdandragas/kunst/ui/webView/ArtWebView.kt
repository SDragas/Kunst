package hr.ferit.srdandragas.kunst.ui.webView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import hr.ferit.srdandragas.kunst.R
import hr.ferit.srdandragas.kunst.repository.ArtDetailsRepository

class ArtWebView : AppCompatActivity() {

    private val repository = ArtDetailsRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_art_web_view)
        setupUi()
    }

    private fun setupUi() {
        val url = intent.getStringExtra(WEB_URL)
        val artWebView: WebView = findViewById(R.id.artWebView)
        artWebView.webViewClient = WebViewClient()
        artWebView.settings.javaScriptEnabled = true
        artWebView.loadUrl(url)
    }
    companion object{
        const val WEB_URL = "Url"
    }
}

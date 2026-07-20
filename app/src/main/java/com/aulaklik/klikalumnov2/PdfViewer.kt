package com.aulaklik.klikalumnov2

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.barteksc.pdfviewer.PDFView
import java.net.URL
import kotlin.concurrent.thread

class PdfViewer: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pdfViewer_activity)) { v, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
            insets
        }

        val pdfUrl = intent.getStringExtra("pdfUrl") ?: ""
        val pdfName = intent.getStringExtra("pdfName") ?: "PDF Viewer"

        if (pdfUrl == "") {
            Toast.makeText(this, "No se proporcionó la URL del PDF", Toast.LENGTH_SHORT).show()
        } else {
            val pdfView = findViewById<PDFView>(R.id.pdfView_container)
            val pdfTitle = findViewById<TextView>(R.id.pdf_header_title)
            pdfTitle.text = pdfName

            thread {
                try {
                    val inputStream = URL(pdfUrl).openStream()

                    runOnUiThread {
                        pdfView.fromStream(inputStream)
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .defaultPage(0)
                            .load()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this, "Error al cargar el PDF", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Consolidar botón de retroceso
        val backButton = findViewById<ImageView>(R.id.arrow_back)

        backButton.setOnClickListener {
            finish()
        }
    }
}
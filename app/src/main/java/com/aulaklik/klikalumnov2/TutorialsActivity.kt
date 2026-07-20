package com.aulaklik.klikalumnov2

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.reflect.InvocationTargetException
import androidx.core.net.toUri
import coil3.load

class TutorialsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutorials)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tutorials_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val generalServices = GeneralServices()

        val viewTitle = findViewById<TextView>(R.id.tutorials_view_title)
        val viewManual = findViewById<TextView>(R.id.manual_title_text)
        val titleText = getString(R.string.tutorials_view_title)
        val manualText = getString(R.string.manuals_view_title)

        viewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        viewManual.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)

        viewTitle.text = HtmlCompat.fromHtml(titleText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        viewManual.text = HtmlCompat.fromHtml(manualText, HtmlCompat.FROM_HTML_MODE_LEGACY)

        // Initialización de footer
        FooterServices().initStaticFooter(this, this, "tutorials")

        // Renderizar información de tutoriales y manuales
        try {
            val activeControllers = mutableListOf<MediaController>()

            generalServices.sendGetRequest(this, "http://200.55.49.23/app/tutoriales.php?usu=6") { responseJson ->
                val responseObject = JSONObject(responseJson)

                val responseTutorials = JSONArray(responseObject.getString("tutorial"))
                val responseManuals = JSONArray(responseObject.getString("manual"))

                for (i in 0 until responseTutorials.length()) {
                    val wrapperVideos = findViewById<LinearLayout>(R.id.list_tutorials)

                    val containerVideos = ConstraintLayout(this).apply {
                        id = View.generateViewId()
                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.setMargins(0, 0, 0, 40)

                        layoutParams = params
                    }

                    val titleVideos = TextView(this).apply {
                        id = View.generateViewId()

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID

                        layoutParams = params

                        text = responseTutorials.getJSONObject(i).getString("name")
                        textSize = 18f
                        setTextColor(getColor(R.color.black))
                        setTypeface(typeface, android.graphics.Typeface.BOLD)
                    }

                    val heightVideo = 500

                    val video = VideoView(this).apply {
                        id = View.generateViewId()

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            heightVideo
                        )

                        params.topToBottom = titleVideos.id
                        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        params.setMargins(0, 20, 0, 0)

                        setVideoURI(
                            "http://200.55.49.23/assets/uploads/tutorials/${
                                responseTutorials.getJSONObject(
                                    i
                                ).getString("file")
                            }".toUri())

                        val mc = MediaController(this@TutorialsActivity)
                        setMediaController(mc)
                        activeControllers.add(mc)

                        layoutParams = params
                    }

                    val imageCover = ImageView(this).apply {
                        id = View.generateViewId()

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            heightVideo
                        )

                        setBackgroundColor(getColor(R.color.white))

                        params.topToBottom = titleVideos.id
                        params.startToStart = video.id
                        params.endToEnd = video.id
                        adjustViewBounds = true

                        layoutParams = params
                        scaleType = ImageView.ScaleType.CENTER_CROP

                        setPadding(120, 120, 120, 120)
                    }

                    imageCover.load("http://200.55.49.23/app/logo.png")

                    video.setOnPreparedListener { mp ->
                        imageCover.setOnClickListener {
                            video.start()
                            imageCover.visibility = View.GONE
                        }
                    }

                    containerVideos.addView(titleVideos)
                    containerVideos.addView(video)
                    containerVideos.addView(imageCover)

                    wrapperVideos.addView(containerVideos)
                }

                for (i in 0 until responseManuals.length()) {
                    val wrapperManuals = findViewById<LinearLayout>(R.id.list_manuals)

                    val containerManuals = ConstraintLayout(this).apply {
                        id = View.generateViewId()

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID

                        params.setMargins(10, 10, 10, 40)

                        layoutParams = params
                    }

                    val pdfImg = ImageView(this).apply {
                        id = View.generateViewId()

                        val params = ConstraintLayout.LayoutParams(
                            252,
                            252
                        )

                        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID

                        setImageDrawable(getDrawable(R.drawable.pdf_icono))

                        layoutParams = params
                    }

                    val titleManuals = TextView(this).apply {
                        id = View.generateViewId()
                        text = responseManuals.getJSONObject(i).getString("name")

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.topToBottom = pdfImg.id
                        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                        setTextColor(getColor(R.color.black))
                        setTypeface(typeface, android.graphics.Typeface.BOLD)

                        layoutParams = params
                    }

                    containerManuals.addView(pdfImg)
                    containerManuals.addView(titleManuals)

                    containerManuals.setOnClickListener {
                        generalServices.openIntentPdfViewer(
                            this,
                            "http://200.55.49.23/assets/uploads/manuals/${responseManuals.getJSONObject(i).getString("file")}",
                            responseManuals.getJSONObject(i).getString("name")
                        )
                    }

                    wrapperManuals.addView(containerManuals)
                }
            }

            // Ocultamiento de controles de video al hacer scroll
            val scrollView = findViewById<ScrollView>(R.id.tutorials_main_view)
            scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
                for (controller in activeControllers) {
                    controller.hide()
                }
            }
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al cargar tutoriales y manuales", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
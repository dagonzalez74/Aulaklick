package com.aulaklik.klikalumnov2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setMargins

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Llenado de descripciones y mensajes con HTML
        val descriptionWindow = findViewById<TextView>(R.id.window_description_title)
        val descriptionWindowTitle = getString(R.string.window_description_title)

        val firstDescriptionView = findViewById<TextView>(R.id.window_first_description)
        val firstDescriptionText = getString(R.string.window_first_description)

        val secondDescriptionView = findViewById<TextView>(R.id.window_second_description)
        val secondDescriptionText = getString(R.string.window_second_description)

        descriptionWindow.text = HtmlCompat.fromHtml(descriptionWindowTitle, HtmlCompat.FROM_HTML_MODE_LEGACY)
        firstDescriptionView.text = HtmlCompat.fromHtml(firstDescriptionText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        secondDescriptionView.text = HtmlCompat.fromHtml(secondDescriptionText, HtmlCompat.FROM_HTML_MODE_LEGACY)

        // Tintado de la vista en la que se encuentra el usuario en el menú del footer
        FooterServices().initStaticFooter(this, this, "home")
    }
}
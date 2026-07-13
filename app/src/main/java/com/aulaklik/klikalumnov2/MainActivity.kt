package com.aulaklik.klikalumnov2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Llenado de descripción y del mensaje de bienvenida con HTML
        val descriptionView = findViewById<TextView>(R.id.description)
        val welcomeMessage = findViewById<TextView>(R.id.welcome_message)
        val descriptionText = getString(R.string.description_text)
        val welcomeText = getString(R.string.welcome_message)

        descriptionView.text = HtmlCompat.fromHtml(descriptionText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        welcomeMessage.text = HtmlCompat.fromHtml(welcomeText, HtmlCompat.FROM_HTML_MODE_LEGACY)

        // Botón para el inicio de sesión
        val btnAccess = findViewById<Button>(R.id.button_access)

        btnAccess.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        // Botón para el "olvidé mi contraseña"
        val btnForgotPassword = findViewById<TextView>(R.id.forgot_password)

        btnForgotPassword.setOnClickListener {
            Toast.makeText(this, "Se me olvidó mi contraseña", Toast.LENGTH_SHORT).show()
        }
    }
}
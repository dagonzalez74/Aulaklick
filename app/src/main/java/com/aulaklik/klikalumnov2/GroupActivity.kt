package com.aulaklik.klikalumnov2

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GroupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_groups)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.groups_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Renderizado de textos
        val viewTitle = findViewById<TextView>(R.id.groups_view_title)
        val viewTitleText = HtmlCompat.fromHtml(getString(R.string.groups_view_title), HtmlCompat.FROM_HTML_MODE_LEGACY)
        viewTitle.text = viewTitleText

        val groupsIcon = findViewById<ImageView>(R.id.menu_groups)
        val homeIcon = findViewById<ImageView>(R.id.menu_home)

        val firstDivisor = findViewById<View>(R.id.first_vertical_divisor)
        val secondDivisor = findViewById<View>(R.id.second_vertical_divisor)
        val generalServices = GeneralServices()

        generalServices.setIconMenuToActive(groupsIcon, firstDivisor, secondDivisor)

        homeIcon.setOnClickListener {
            generalServices.setIconMenuStartActivity(this, HomeActivity::class.java)
        }
    }
}
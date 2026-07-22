package com.aulaklik.klikalumnov2

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray

class GroupToDoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_group_to_do)
        setSupportActionBar(findViewById(R.id.activity_toolbar))
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.group_to_do_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val generalServices = GeneralServices()
        val groupId = intent.getIntExtra("groupId", 1)
        handleUserOptions()

        generalServices.sendGetRequest(this, "http://200.55.49.23/app/primeraact.php?group=${groupId}") { responseJson ->
            val response = JSONArray(responseJson)

            val lineGroupTitle = findViewById<TextView>(R.id.title_container)

            if (response.length() == 0) {
                lineGroupTitle.text = "No hay tareas disponibles"
            }

            for(i in 0 until response.length()) {
                lineGroupTitle.text = response.getJSONObject(i).getString("nameblock")
            }
        }
    }

    fun handleUserOptions() {
        val usernameContainer = findViewById<ConstraintLayout>(R.id.username_container)
        val userLabel = findViewById<TextView>(R.id.username_label)

        userLabel.text = "Alumno"

        usernameContainer.setOnClickListener {
            Toast.makeText(this, "Username clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
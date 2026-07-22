package com.aulaklik.klikalumnov2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import coil3.load
import org.json.JSONArray

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

        // Inicialización de footer
        FooterServices().initStaticFooter(this, this, "groups")

        // Llamado de datos de grupos por estudiante
        fillViewWithData()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun fillViewWithData() {
        try {
            val generalServices = GeneralServices()
            val wrapper = findViewById<LinearLayout>(R.id.groups_list)

            generalServices.sendGetRequest(this, "http://200.55.49.23/app/gruposalumno.php?usu=6") { responseJson ->
                val response = JSONArray(responseJson)

                for (i in 0 until response.length()) {
                    val group = response.getJSONObject(i)

                    val groupLayout = ConstraintLayout(this).apply {
                        layoutParams = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, 30, 0, 0)
                            id = View.generateViewId()
                        }
                        background = ContextCompat.getDrawable(this@GroupActivity, R.drawable.bg_semi_blue_rounded)
                        setPadding(10, 10, 10, 10)
                    }

                    val innerGroupView = ConstraintLayout(this).apply {
                        layoutParams = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )
                        background = ContextCompat.getDrawable(this@GroupActivity, R.drawable.bg_white_rounded)
                    }

                    // Llenado del título a mostrar
                    lateinit var titleTxt: String

                    when {
                        group.getString("name_materials") != "null" -> titleTxt = group.getString("name_materials")
                        group.getString("name_material_group") != "null" -> titleTxt = group.getString("name_material_group")
                        else -> titleTxt = "Sin título disponible"
                    }

                    val titleId = View.generateViewId()
                    val titleText = TextView(this).apply {
                        id = titleId
                        text = titleTxt
                        background = ContextCompat.getDrawable(this@GroupActivity, R.drawable.bg_title_semi_blue)
                        setTextColor(ContextCompat.getColor(this@GroupActivity, android.R.color.white))
                        setTypeface(typeface, android.graphics.Typeface.BOLD)

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        setPadding(15, 10, 15, 10)
                        setTypeface(typeface, android.graphics.Typeface.BOLD)

                        params.setMargins(0, 20, 220, 0)
                        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
                        layoutParams = params
                    }

                    val descriptionContainer = LinearLayout(this).apply {
                        id = View.generateViewId()
                        orientation = LinearLayout.HORIZONTAL
                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.topToBottom = titleId

                        layoutParams = params
                    }

                    val descriptionImg = ImageView(this).apply {
                        id = View.generateViewId()
                        val params = ConstraintLayout.LayoutParams(
                            250,
                            350
                        )
                        params.setMargins(100, 20, 0, 20)
                        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

                        adjustViewBounds = true

                        layoutParams = params
                    }

                    lateinit var descriptionImgName: String

                    when {
                        group.getString("frontpage") != "null" -> descriptionImgName = group.getString("frontpage")
                        group.getString("frontpage_group") != "null" -> descriptionImgName = group.getString("frontpage_group")
                        else -> descriptionImgName = "barra_klik.png"
                    }

                    descriptionImg.load("http://200.55.49.23/assets/uploads/frontpages/${descriptionImgName}")

                    val infoWrapper = LinearLayout(this).apply {
                        id = View.generateViewId()
                        orientation = LinearLayout.VERTICAL
                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.leftToRight = descriptionImg.id
                        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
                        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                        setPadding(30, 30, 30, 30)

                        layoutParams = params
                    }

                    val textViewIntitution = TextView(this).apply {
                        id = View.generateViewId()
                        text = if (group.getString("name_institucion") != "null") group.getString("name_institucion") else "Sin institución disponible"

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID

                        setTextColor(getColor(R.color.black))

                        layoutParams = params
                    }

                    val textViewSemester = TextView(this).apply {
                        id = View.generateViewId()
                        val numSemester = if (group.getString("nivel_educativo") != "null") group.getString("nivel_educativo") + "°" else "Sin semestre disponible"
                        val scholarShipModel = if (group.getString("modelo_escolar") != "null") group.getString("modelo_escolar") else "Sin modelo educativo disponible"
                        text = "$numSemester $scholarShipModel"

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.topToBottom = textViewIntitution.id
                        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID

                        setTextColor(getColor(R.color.black))

                        layoutParams = params
                    }

                    val textViewNumStudents = TextView(this).apply {
                        id = View.generateViewId()
                        text = "Alumnos inscritos: " + if (group.getString("inscritos") != "null") group.getString("inscritos") else "0"

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.topToBottom = textViewSemester.id
                        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID

                        setTextColor(getColor(R.color.black))

                        layoutParams = params
                    }

                    val textViewStart = TextView(this).apply {
                        id = View.generateViewId()
                        text = "Inicia: " + if (group.getString("fecha_inicio") != "null") group.getString("fecha_inicio") else "Sin fecha disponible"

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.topToBottom = textViewNumStudents.id
                        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID

                        setTextColor(getColor(R.color.black))

                        layoutParams = params
                    }

                    val textViewEnd = TextView(this).apply {
                        id = View.generateViewId()
                        text = "Termina: " + if (group.getString("fecha_termino") != "null") group.getString("fecha_termino") else "Sin fecha disponible"

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.topToBottom = textViewStart.id
                        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID

                        setTextColor(getColor(R.color.black))

                        layoutParams = params
                    }

                    val btnEnterGroup = Button(this).apply {
                        id = View.generateViewId()

                        text = "Ir al grupo"

                        val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        params.setMargins(0, 20, 0, 0)

                        compoundDrawablePadding = 10

                        background = generalServices.createRoundedBorderDrawable(
                            25f,
                            getColor(R.color.orange),
                            2,
                            getColor(R.color.orange)
                        )

                        setPadding(20, 10, 20, 10)
                        setTextColor(getColor(R.color.white))

                        setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.ir_al_grupo), null)

                        layoutParams = params
                    }

                    btnEnterGroup.setOnClickListener {
                        val intent = Intent(this, GroupToDoActivity::class.java)
                        startActivity(intent)
                    }

                    infoWrapper.addView(textViewIntitution)
                    infoWrapper.addView(textViewSemester)
                    infoWrapper.addView(textViewNumStudents)
                    infoWrapper.addView(textViewStart)
                    infoWrapper.addView(textViewEnd)
                    infoWrapper.addView(btnEnterGroup)

                    descriptionContainer.addView(descriptionImg)
                    descriptionContainer.addView(infoWrapper)

                    innerGroupView.addView(titleText)
                    innerGroupView.addView(descriptionContainer)

                    groupLayout.addView(innerGroupView)

                    wrapper.addView(groupLayout)
                }
            }
        } catch (e: Exception) {
            Log.e("GroupActivity", "Error filling view with data: ${e.message}")
            Toast.makeText(this, "Error loading groups: ${e.message}", LENGTH_SHORT).show()
        }
    }
}
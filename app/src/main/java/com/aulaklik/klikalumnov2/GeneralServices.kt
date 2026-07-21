package com.aulaklik.klikalumnov2

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class GeneralServices {
    fun setIconMenuToActive(icon: ImageView, leftBar: View?, rightBar: View?) {
        icon.setColorFilter(icon.context.getColor(R.color.white))
        icon.setBackgroundColor(icon.context.getColor(R.color.semi_blue))
        icon.layoutParams.width = 0
        icon.layoutParams.height = 0

        val marginParams = icon.layoutParams as ConstraintLayout.LayoutParams
        marginParams.setMargins(0, 0, 0, 0)
        icon.layoutParams = marginParams
        icon.setPadding(20, 20, 20, 20)

        leftBar?.setBackgroundColor(icon.context.getColor(R.color.white))
        rightBar?.setBackgroundColor(icon.context.getColor(R.color.white))
    }

    fun setIconMenuStartActivity(context: Context, javaClass: Class<*>) {
        val intent = android.content.Intent(context, javaClass)
        context.startActivity(intent)
    }

    fun sendGetRequest(context: Context, url: String, callback: (String) -> Unit) {
        try {
            val queue = Volley.newRequestQueue(context)

            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                     callback(response)
                },
                { error ->
                    error.printStackTrace()
                })

            queue.add(stringRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendPostRequest(context: Context, url: String, callback: (String) -> Unit) {
        try {
            val queue = Volley.newRequestQueue(context)

            val stringRequest = StringRequest(Request.Method.POST, url,
                { response ->
                    callback(response)
                },
                { error ->
                    error.printStackTrace()
                })

            queue.add(stringRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openIntentPdfViewer(context: Context, pdfUrl: String, pdfName: String?) {
        val intent = android.content.Intent(context, PdfViewer::class.java)
        intent.putExtra("pdfUrl", pdfUrl)
        intent.putExtra("pdfName", pdfName)
        context.startActivity(intent)
    }

    fun createRoundedBorderDrawable(cornerRadius: Float, borderColor: Int, borderWidth: Int, backgroundColor: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(backgroundColor)
            setStroke(borderWidth, borderColor)
            this.cornerRadius = cornerRadius
        }
    }
}
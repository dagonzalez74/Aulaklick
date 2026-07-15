package com.aulaklik.klikalumnov2

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

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


}
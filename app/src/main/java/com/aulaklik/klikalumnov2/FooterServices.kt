package com.aulaklik.klikalumnov2

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.viewbinding.ViewBindings

class FooterServices {
    fun initStaticFooter(context: android.content.Context, activity: Activity, iconActive: String) {
        val generalServices = GeneralServices()

        val groupsIcon = activity.findViewById<ImageView>(R.id.menu_groups)
        val homeIcon = activity.findViewById<ImageView>(R.id.menu_home)
        val tutorialIcon = activity.findViewById<ImageView>(R.id.menu_tutorials)
        val usersIcon = activity.findViewById<ImageView>(R.id.menu_footer_user)

        val firstDivisor = activity.findViewById<View>(R.id.first_vertical_divisor)
        val secondDivisor = activity.findViewById<View>(R.id.second_vertical_divisor)
        val thirdDivisor = activity.findViewById<View>(R.id.third_vertical_divisor)

        when (iconActive) {
            "groups" -> {
                generalServices.setIconMenuToActive(groupsIcon, firstDivisor, secondDivisor)
            }
            "home" -> {
                generalServices.setIconMenuToActive(homeIcon, null, firstDivisor)
            }
            "tutorials" -> {
                generalServices.setIconMenuToActive(tutorialIcon, secondDivisor, thirdDivisor)
            }
            "users" -> {
                generalServices.setIconMenuToActive(usersIcon, thirdDivisor, null)
            }
        }

        homeIcon.setOnClickListener {
            generalServices.setIconMenuStartActivity(context, HomeActivity::class.java)
        }

        tutorialIcon.setOnClickListener {
            generalServices.setIconMenuStartActivity(context, TutorialsActivity::class.java)
        }

        groupsIcon.setOnClickListener {
            generalServices.setIconMenuStartActivity(context, GroupActivity::class.java)
        }


    }
}
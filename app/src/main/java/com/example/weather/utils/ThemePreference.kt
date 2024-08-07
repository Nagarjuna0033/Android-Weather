package com.example.weather.utils

import android.content.Context


class ThemePreference(context: Context) {


    private val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun getThemePreference(): ThemePreferenceClass {
        val isDarkTheme = sharedPreferences.getBoolean("mode", false)
        return ThemePreferenceClass(isDarkTheme)
    }

    fun setThemePreference(themePreferenceClass: ThemePreferenceClass) {
        editor.putBoolean("mode", themePreferenceClass.isDarkTheme)
        editor.apply()
    }
}
data class ThemePreferenceClass(val isDarkTheme: Boolean)
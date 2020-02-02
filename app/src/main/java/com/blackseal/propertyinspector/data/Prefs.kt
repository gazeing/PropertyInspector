package com.blackseal.propertyinspector.data

import android.content.Context
import android.content.SharedPreferences

class Prefs(val appContext: Context) {
    companion object {
        private const val USER_PREFERENCES_NAME = "InspectorPrefs"
        private const val PREF_SUBURB_PERFERENCE_RATE = "PREF_SUBURB_PERFERENCE_RATE"
        private const val PREF_DECORATION_PERFERENCE_RATE = "PREF_DECORATION_PERFERENCE_RATE"
        private const val PREF_LOCATION_PERFERENCE_RATE = "PREF_LOCATION_PERFERENCE_RATE"
        private const val PREF_SHAPE_PERFERENCE_RATE = "PREF_SHAPE_PERFERENCE_RATE"
    }

    fun getPreferences(): SharedPreferences {
        return getPreferences(context = appContext)
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun getSuburbPrefRate(): Float {
        return getPreferences().getFloat(PREF_SUBURB_PERFERENCE_RATE, 22f / 43f)
    }

    fun setSuburbPrefRate(rate: Float) {
        getPreferences().edit().putFloat(PREF_SUBURB_PERFERENCE_RATE, rate).apply()
    }

    fun getDecorationPrefRate(): Float {
        return getPreferences().getFloat(PREF_SUBURB_PERFERENCE_RATE, 22f / 43f)
    }

    fun setDecorationPrefRate(rate: Float) {
        getPreferences().edit().putFloat(PREF_SUBURB_PERFERENCE_RATE, rate).apply()
    }

    fun getLocationPrefRate(): Float {
        return getPreferences().getFloat(PREF_SUBURB_PERFERENCE_RATE, 22f / 43f)
    }

    fun setLocationPrefRate(rate: Float) {
        getPreferences().edit().putFloat(PREF_SUBURB_PERFERENCE_RATE, rate).apply()
    }

    fun getShapePrefRate(): Float {
        return getPreferences().getFloat(PREF_SUBURB_PERFERENCE_RATE, 22f / 43f)
    }

    fun setShapePrefRate(rate: Float) {
        getPreferences().edit().putFloat(PREF_SUBURB_PERFERENCE_RATE, rate).apply()
    }
}
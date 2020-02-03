package com.blackseal.propertyinspector.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class Prefs(val appContext: Context) {
    companion object {
        private const val USER_PREFERENCES_NAME = "InspectorPrefs"
        private const val PREF_SUBURB_PERFERENCE_RATE = "PREF_SUBURB_PERFERENCE_RATE"
        private const val PREF_DECORATION_PERFERENCE_RATE = "PREF_DECORATION_PERFERENCE_RATE"
        private const val PREF_LOCATION_PERFERENCE_RATE = "PREF_LOCATION_PERFERENCE_RATE"
        private const val PREF_SHAPE_PERFERENCE_RATE = "PREF_SHAPE_PERFERENCE_RATE"
        private const val PREF_BASE_RANK = "PREF_BASE_RANK"
    }

    fun getPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun getBaseRankPref(): Int {
        return getPreferences().getString(PREF_BASE_RANK, "108")!!.toInt()
    }

    fun setBaseRankPref(rate: Int) {
        getPreferences().edit().putInt(PREF_BASE_RANK, rate).apply()
    }

    fun getSuburbPrefRate(): Float {
        return getPreferences().getString(PREF_SUBURB_PERFERENCE_RATE, "0.5116")!!.toFloat()
    }

    fun setSuburbPrefRate(rate: Float) {
        getPreferences().edit().putFloat(PREF_SUBURB_PERFERENCE_RATE, rate).apply()
    }

    fun getDecorationPrefRate(): Float {
        return getPreferences().getString(PREF_DECORATION_PERFERENCE_RATE, "0.5116")!!.toFloat()
    }

    fun setDecorationPrefRate(rate: Float) {
        getPreferences().edit().putFloat(PREF_DECORATION_PERFERENCE_RATE, rate).apply()
    }

    fun getLocationPrefRate(): Float {
        return getPreferences().getString(PREF_LOCATION_PERFERENCE_RATE, "0.5116")!!.toFloat()
    }

    fun setLocationPrefRate(rate: Float) {
        getPreferences().edit().putFloat(PREF_LOCATION_PERFERENCE_RATE, rate).apply()
    }

    fun getShapePrefRate(): Float {
        return getPreferences().getString(PREF_SHAPE_PERFERENCE_RATE, "0.5116")!!.toFloat()
    }

    fun setShapePrefRate(rate: Float) {
        getPreferences().edit().putFloat(PREF_SHAPE_PERFERENCE_RATE, rate).apply()
    }
}
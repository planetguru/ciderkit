package com.uk.christo.ciderKit.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREFS_NAME = "cider_kit_prefs"
        
        // AddSugar tab keys
        private const val KEY_STARTING_SG = "starting_sg"
        private const val KEY_TARGET_SG = "target_sg"
        private const val KEY_VOLUME = "volume"
        private const val KEY_IS_IMPERIAL = "is_imperial"
        
        // SGToAlc tab keys
        private const val KEY_ORIGINAL_GRAVITY = "original_gravity"
        private const val KEY_TERMINAL_GRAVITY = "terminal_gravity"
    }
    
    // AddSugar fragment data
    fun saveAddSugarData(startingSG: String, targetSG: String, volume: String, isImperial: Boolean) {
        prefs.edit().apply {
            putString(KEY_STARTING_SG, startingSG)
            putString(KEY_TARGET_SG, targetSG)
            putString(KEY_VOLUME, volume)
            putBoolean(KEY_IS_IMPERIAL, isImperial)
            apply()
        }
    }
    
    fun getStartingSG(): String = prefs.getString(KEY_STARTING_SG, "") ?: ""
    fun getTargetSG(): String = prefs.getString(KEY_TARGET_SG, "") ?: ""
    fun getVolume(): String = prefs.getString(KEY_VOLUME, "") ?: ""
    fun isImperialSelected(): Boolean = prefs.getBoolean(KEY_IS_IMPERIAL, false)
    
    // SGToAlc fragment data
    fun saveSGToAlcData(originalGravity: String, terminalGravity: String) {
        prefs.edit().apply {
            putString(KEY_ORIGINAL_GRAVITY, originalGravity)
            putString(KEY_TERMINAL_GRAVITY, terminalGravity)
            apply()
        }
    }
    
    fun getOriginalGravity(): String = prefs.getString(KEY_ORIGINAL_GRAVITY, "") ?: ""
    fun getTerminalGravity(): String = prefs.getString(KEY_TERMINAL_GRAVITY, "") ?: ""
}
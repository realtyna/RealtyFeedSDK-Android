package com.realtyna.realtyfeedsdksample

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject

open class PreferenceHelper constructor(val context: Context){

    private fun customPrefs(): SharedPreferences
            = context.getSharedPreferences("SPL_SHARED_PREF", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String) = customPrefs().getString(key, defValue)
    fun getInt(key: String, defValue: Int) = customPrefs().getInt(key, defValue)
    fun getBoolean(key: String, defValue: Boolean) = customPrefs().getBoolean(key, defValue)
    fun getLong(key: String, defValue: Long) = customPrefs().getLong(key, defValue)
    fun getFloat(key: String, defValue: Float) = customPrefs().getFloat(key, defValue)
    fun putString(valueString: String,keyString: String) = customPrefs().edit {
            editor -> editor.run {
        this.putString(keyString,valueString)
    }
    }
    fun putInt( valueInt: Int, keyString: String) = customPrefs().edit {
            editor -> editor.run {
        this.putInt(keyString,valueInt)
    }
    }
    fun putBool(valueBool: Boolean, keyString: String) = customPrefs().edit {
            editor -> editor.run {
        this.putBoolean(keyString,valueBool)
    }
    }
    fun putLong(valueLong: Long, keyString: String) = customPrefs().edit {
            editor -> editor.run {
        this.putLong(keyString,valueLong)
    }
    }
    fun putFloat(valueFloat: Float, keyString: String) = customPrefs().edit {
            editor -> editor.run {
        this.putFloat(keyString,valueFloat)
    }
    }

    fun putArray(array: ArrayList<JSONObject>, keyString: String) = customPrefs().edit {
            editor -> editor.run {
        val set: MutableSet<String> = HashSet()
        for (obj in array){
            set.add(obj.toString())
        }
        this.putStringSet(keyString,set)
    }
    }
    fun getArray(keyString: String): ArrayList<JSONObject> {
        val array : ArrayList<JSONObject> = arrayListOf()
        val set = customPrefs().getStringSet(keyString, null)
        if (set != null){
            for (so in set){
                array.add(JSONObject(so))
            }
        }
        return array
    }

}
fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}
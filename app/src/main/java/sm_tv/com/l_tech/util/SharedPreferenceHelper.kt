package sm_tv.com.l_tech.util

import android.content.Context
import sm_tv.com.l_tech.util.Constants.CURRENT_MASK
import sm_tv.com.l_tech.util.Constants.SHARED_NAME
import sm_tv.com.l_tech.util.Constants.USER_PASSWORD
import sm_tv.com.l_tech.util.Constants.USER_PHONE

object SharedPreferenceHelper {

    fun saveInShared(phone: String, password: String, context: Context) {
        val prefsSetting = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        val editor = prefsSetting.edit()
        editor.putString(USER_PHONE, phone)
        editor.putString(USER_PASSWORD, password)
        editor.apply()
    }

    fun saveMask(currentMask: String, context: Context) {
        val prefsSetting = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        val editor = prefsSetting.edit()
        editor.putString(CURRENT_MASK, currentMask)
        editor.apply()
    }

    fun readInShared(context: Context): UserData? {
        val prefsSetting = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        return if (prefsSetting.contains(USER_PHONE)
            && prefsSetting.contains(USER_PASSWORD)
            && prefsSetting.contains(CURRENT_MASK)
        ) {
            val phone = prefsSetting.getString(USER_PHONE, "")
            val password = prefsSetting.getString(USER_PASSWORD, "")
            val currentMask = prefsSetting.getString(CURRENT_MASK, "")
            UserData(phone, password, currentMask)
        } else null
    }

}

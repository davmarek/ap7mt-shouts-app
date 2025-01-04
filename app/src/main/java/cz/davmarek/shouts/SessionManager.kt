package cz.davmarek.shouts


import android.content.Context
import android.content.SharedPreferences
import com.auth0.jwt.JWT

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        if (isTokenExpired(token)) {
            clearAuthToken()
            return
        }
        editor.putString(ACCESS_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        val token = prefs.getString(ACCESS_TOKEN, null)
        if (token != null && isTokenExpired(token)) {
            clearAuthToken()
            return null
        }
        return token
    }


    /**
     * Function to clear auth token
     */
    fun clearAuthToken() {
        val editor = prefs.edit()
        editor.remove(ACCESS_TOKEN)
        editor.apply()
    }

    /**
     * Function to check if token is expired
     */
    private fun isTokenExpired(token: String): Boolean {
        try {
            val jwt = JWT().decodeJwt(token)
            return jwt.expiresAt?.time?.let { it < System.currentTimeMillis() }
                ?: true
        } catch (e: Exception) {
            return true
        }

    }
}
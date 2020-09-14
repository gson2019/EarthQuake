package com.example.bubble.earthquake.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.bubble.earthquake.model.EarthQuakeResponse
import com.example.bubble.earthquake.model.Earthquake
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

object EarthQuakeUtil {
    fun readStream(input: InputStream): String? {
        var reader: BufferedReader? = null
        val data = StringBuffer()
        try {
            reader = BufferedReader(InputStreamReader(input))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                data.append(line)
            }
        } catch (e: IOException) {
            Log.e("EarthQuakeRequest", "IOException")
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return data.toString()
    }

    fun extractEarthquakes(jsonResponse: String?): EarthQuakeResponse {
        var earthquakes:EarthQuakeResponse? = null
        try {
            earthquakes = Gson().fromJson<EarthQuakeResponse>(
                jsonResponse,
                object : TypeToken<EarthQuakeResponse>() {}.type
            )
        } catch (e: JSONException) {
            Log.e("ParsingError", "Problem parsing EarthQuake JSON result", e)
        }
        return earthquakes!!
    }

    /**
     * Check network conctivity
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}
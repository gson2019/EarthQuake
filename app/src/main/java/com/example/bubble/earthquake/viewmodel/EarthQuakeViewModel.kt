package com.example.bubble.earthquake.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bubble.earthquake.BuildConfig
import com.example.bubble.earthquake.model.EarthQuakeResponse
import com.example.bubble.earthquake.model.EarthQuakeWrapper
import com.example.bubble.earthquake.network.AppExecutors
import com.example.bubble.earthquake.utils.EarthQuakeUtil
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * The only View Model for this App
 */
class EarthQuakeViewModel : ViewModel() {
    val earthQuakeLiveData: MutableLiveData<EarthQuakeWrapper<EarthQuakeResponse>> by lazy{ MutableLiveData<EarthQuakeWrapper<EarthQuakeResponse>>() }
    private val tag = "EarthQuakeRequest"
    fun getEarthQuakeList(context:Context) {
        when (EarthQuakeUtil.isNetworkAvailable(context)) {
            true -> {
                AppExecutors.instance.networkIO().execute {
                    var httpUrlConnection : HttpURLConnection? = null
                    try {
                        val url = URL(BuildConfig.EARTHQUAKE_URL)
                        httpUrlConnection = url.openConnection() as HttpURLConnection
                        val inputStream = BufferedInputStream(httpUrlConnection.inputStream)
                        val earthStr = EarthQuakeUtil.readStream(inputStream)
                        val earthquakes = EarthQuakeUtil.extractEarthquakes(earthStr)
                        earthQuakeLiveData.postValue(EarthQuakeWrapper.success(earthquakes))
                    } catch(exception : MalformedURLException) {
                        Log.e(tag, "Malformed URL Exception");
                    } catch (exception : IOException) {
                        Log.e(tag, exception.message+"");
                    } finally {
                        httpUrlConnection?.disconnect();
                    }
                }
            }
            false -> {
                earthQuakeLiveData.postValue(EarthQuakeWrapper.error(null, "Network Error, please check your connectivity"))
            }
        }

    }
}
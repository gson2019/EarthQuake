package com.example.bubble.earthquake.model

/**
 *
 */
data class EarthQuakeWrapper<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): EarthQuakeWrapper<T> = EarthQuakeWrapper(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): EarthQuakeWrapper<T> =
            EarthQuakeWrapper(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): EarthQuakeWrapper<T> = EarthQuakeWrapper(status = Status.LOADING, data = data, message = null)
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}
package com.example.bubble.earthquake.model

/**
 * @constructor
 * @param earthquakes {This is the actual data used in the App)
 */
data class EarthQuakeResponse(
    val earthquakes: List<Earthquake>
)
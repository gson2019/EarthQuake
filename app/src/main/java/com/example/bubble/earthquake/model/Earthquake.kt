package com.example.bubble.earthquake.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Rui Liu
 * @param datetime { date time info}
 */
data class Earthquake(
    val datetime: String,
    val depth: Double,
    val eqid: String,
    val lat: Double,
    val lng: Double,
    val magnitude: Double,
    val src: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(datetime)
        parcel.writeDouble(depth)
        parcel.writeString(eqid)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
        parcel.writeDouble(magnitude)
        parcel.writeString(src)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Earthquake> {
        override fun createFromParcel(parcel: Parcel): Earthquake {
            return Earthquake(parcel)
        }

        override fun newArray(size: Int): Array<Earthquake?> {
            return arrayOfNulls(size)
        }
    }
}
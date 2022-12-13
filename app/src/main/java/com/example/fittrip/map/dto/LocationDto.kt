package com.example.fittrip.map.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationDto(var x: Double?, var y: Double?, var name: String?) : Parcelable
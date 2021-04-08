package com.example.testingskripsinew.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataStatus(
    var geoStatus: String? = null,
) : Parcelable
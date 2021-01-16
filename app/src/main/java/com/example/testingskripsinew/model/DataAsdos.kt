package com.example.testingskripsinew.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataAsdos(
    var alamat: String? = null,
    var kelas: String? = null,
    var nama: String? = null,
    var npm: String? = null,
    var pass: String? = null,
    var status: String? = null,
    var ttl: String? = null,
) : Parcelable
package com.example.testingskripsinew.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataMatKul(
    var npmPengajar1: String? = null,
    var npmPengajar2: String? = null,
    var hari: String? = null,
    var jam: String? = null,
    var kelas: String? = null,
    var kode: String? = null,
    var nama: String? = null,
    var pengajar1: String? = null,
    var pengajar2: String? = null,
) : Parcelable
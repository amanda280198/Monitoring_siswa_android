package com.example.testingskripsinew.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPertemuan(
    var pertemuan1: String? = null,
    var pertemuan2: String? = null,
    var pertemuan3: String? = null,
    var pertemuan4: String? = null,
    var pertemuan5: String? = null,
    var pertemuan6: String? = null,
    var pertemuan7: String? = null,
    var pertemuan8: String? = null,
    var pertemuan9: String? = null,
    var pertemuan10: String? = null,
) : Parcelable
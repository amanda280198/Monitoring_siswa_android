package com.example.testingskripsinew.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataKelas(
    var izin: String? = null,
    var jamKeluar: String? = null,
    var jamMasuk: String? = null,
    var jarak: String? = null,
    var kode: String? = null,
    var koordinat: String? = null,
    var nama: String? = null,
    var npm: String? = null,
    var statusMasuk: String? = null,
    var statuskeluar: String? = null,
) : Parcelable
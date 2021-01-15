package com.example.testingskripsinew.helper

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testingskripsinew.R
import com.example.testingskripsinew.user.JarakActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.Executors

class BarcodeResultBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_barcode_data, container, false)


    fun updateText(text: String) {
        fetchTextMetaData(text) { kode, nama, kelas, title ->
            view?.apply {
                findViewById<TextView>(R.id.text_view_title)?.text = title
                findViewById<TextView>(R.id.text_view_matkul)?.text = nama
                findViewById<TextView>(R.id.text_view_kelas)?.text = kelas
                if (kode == "0") {
                    findViewById<TextView>(R.id.btn_absen)?.visibility = View.GONE
                } else {
                    findViewById<TextView>(R.id.btn_absen)?.setOnClickListener {
                        val i = Intent(requireContext(), JarakActivity::class.java)
                        requireContext().startActivity(i)
                    }
                }
            }
        }
    }

    private fun fetchTextMetaData(
        text: String,
        callback: (kode: String, nama: String, kelas: String, title: String) -> Unit
    ) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            try {
                val emp: JSONObject = JSONObject(text).getJSONObject("data")
                val kode = emp.getString("kode")
                val nama = emp.getString("nama")
                val kelas = emp.getString("kelas")
                val title = "Jadwal Kelas"
                handler.post {
                    callback(kode, nama, kelas, title)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                val title = "Peringatan!"
                val noDetec = "Tidak Terdeteksi"
                val alert = "Pastikan QR yang di scan benar"
                handler.post {
                    callback("0", noDetec, alert, title)
                }
            }
        }
    }
}
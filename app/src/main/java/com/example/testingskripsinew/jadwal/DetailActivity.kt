package com.example.testingskripsinew.jadwal

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.testingskripsinew.R
import com.example.testingskripsinew.asdos.MonitoringKelasActivity
import com.example.testingskripsinew.databinding.ActivityDetailBinding
import com.example.testingskripsinew.model.DataMatKul
import com.example.testingskripsinew.utils.Data
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_DATA_MATKUL = "data_matkul"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<DataMatKul>(EXTRA_DATA_MATKUL)

        val matkul = item?.nama
        val pengajar1 = item?.pengajar1
        val pengajar2 = item?.pengajar2
        val npmPengajar1 = item?.npmPengajar1
        val npmPengajar2 = item?.npmPengajar2
        val hari = item?.hari
        val jam = item?.jam
        val kelas = item?.kelas
        val kodeMatkul = item?.kode
        val status = Data.status

        binding.mataKuliah.text = matkul
        binding.pengajar1.text = pengajar1
        binding.pengajar2.text = pengajar2
        binding.npmPengajar1.text = npmPengajar1
        binding.npmPengajar2.text = npmPengajar2
        binding.hari.text = hari
        binding.jamTgl.text = jam
        binding.kelas.text = "Kelas $kelas"

        val jsonString = "{\"data\":{\"kode\":\"$kodeMatkul\",\"nama\":\"$matkul\",\"kelas\":\"$kelas\"}}"

        if (status == Data.ASDOS) {
            val bitmap = generateQRCode(jsonString)
            binding.qrCodeImage.setImageBitmap(bitmap)
        } else {
            binding.frameQrcode.visibility = View.GONE
        }

        if (Data.npmAsdos == npmPengajar1 || Data.npmAsdos == npmPengajar2)
            binding.btnLihatKelas.visibility = View.VISIBLE
        else
            binding.btnLihatKelas.visibility = View.GONE
    }

    fun btnBack(view: View) {
        finish()
    }

    private fun generateQRCode(kode: String): Bitmap {
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(kode, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            Log.d("onDetail", "generateQRCode: ${e.message}")
        }
        return bitmap
    }

    fun btnLihatKelas(view: View) {
        val intent = Intent(this, MonitoringKelasActivity::class.java)
        startActivity(intent)
    }
}
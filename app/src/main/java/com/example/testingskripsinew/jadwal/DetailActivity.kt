package com.example.testingskripsinew.jadwal

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.testingskripsinew.databinding.ActivityDetailBinding
import com.example.testingskripsinew.utils.Data
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_STATUS = "status"
        const val EXTRA_KODE_MATKUL = "kode_matkul"
        const val EXTRA_MATA_KULIAH = "matkul"
        const val EXTRA_PENGAJAR_1 = "pengajar_1"
        const val EXTRA_PENGAJAR_2 = "pengajar_2"
        const val EXTRA_NPM_PENGAJAR_1 = "npm_pengajar_1"
        const val EXTRA_NPM_PENGAJAR_2 = "npm_pengajar_1"
        const val EXTRA_HARI = "hari"
        const val EXTRA_JAM = "jam"
        const val EXTRA_KELAS = "kelas"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val matkul = intent.getStringExtra(EXTRA_MATA_KULIAH).toString()
        val pengajar1 = intent.getStringExtra(EXTRA_PENGAJAR_1).toString()
        val pengajar2 = intent.getStringExtra(EXTRA_PENGAJAR_2).toString()
        val npmPengajar1 = intent.getStringExtra(EXTRA_NPM_PENGAJAR_1).toString()
        val npmPengajar2 = intent.getStringExtra(EXTRA_NPM_PENGAJAR_2).toString()
        val hari = intent.getStringExtra(EXTRA_HARI).toString()
        val jam = intent.getStringExtra(EXTRA_JAM).toString()
        val kelas = intent.getStringExtra(EXTRA_KELAS).toString()
        val kodeMatkul = intent.getStringExtra(EXTRA_KODE_MATKUL).toString()
        val status = intent.getStringExtra(EXTRA_STATUS).toString()

        binding.mataKuliah.text = matkul
        binding.pengajar1.text = pengajar1
        binding.pengajar2.text = pengajar2
        binding.npmPengajar1.text = npmPengajar1
        binding.npmPengajar2.text = npmPengajar2
        binding.hari.text = hari
        binding.jamTgl.text = jam
        binding.kelas.text = "Semester dan Kelas $kelas"

        if (status == Data.ASDOS){
            val bitmap = generateQRCode(kodeMatkul)
            binding.qrCodeImage.setImageBitmap(bitmap)
        } else{
            binding.qrCodeImage.visibility = View.GONE
        }
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
}
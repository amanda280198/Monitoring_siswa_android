package com.example.testingskripsinew.helper

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.fragment.app.FragmentManager
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class MyImageAnalyzer(private val fragmentManager: FragmentManager) : ImageAnalysis.Analyzer {
//class MyImageAnalyzer(private val listener: ScanningResultListener) : ImageAnalysis.Analyzer {

    private var isScanning: Boolean = false
    private var bottomSheet = BarcodeResultBottomSheet()

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        scanBarcode(imageProxy)
    }

//    @ExperimentalGetImage
//    private fun scanBarcode(imageProxy: ImageProxy) {
//        imageProxy.image?.let { image ->
//            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
//            val scanner = BarcodeScanning.getClient()
//
//            isScanning = true
//            scanner.process(inputImage)
//                .addOnSuccessListener { barcodes ->
//                    barcodes?.firstOrNull().let { barcode ->
//                        val rawValue = barcode?.rawValue
//                        rawValue?.let {
//                            Log.d("Barcode", it)
////                            listener.onScanned(it)
//                        }
//                    }
//
//                    isScanning = false
//                    imageProxy.close()
//                }.addOnFailureListener {
//                    isScanning = false
//                    imageProxy.close()
//                }
//        }
//    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private fun scanBarcode(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(inputImage)
                .addOnCompleteListener {
                    imageProxy.close()
                    if (it.isSuccessful) {
                        readBarcodeData(it.result as List<Barcode>)
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    private fun readBarcodeData(barcodes: List<Barcode>) {
        for (barcode in barcodes) {
            val srt: String? = barcode.rawValue
//            Log.d("isi : ", srt.toString())
            if (!bottomSheet.isAdded)
                bottomSheet.show(fragmentManager, "")
            bottomSheet.updateText(srt.toString())
        }
    }

}
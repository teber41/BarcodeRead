package com.example.barcoderead

import android.content.Context
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

//Toast göstermek için constructor'a context gönderiyoruz.
class MyImageAnalyzer(private val context: Context) : ImageAnalysis.Analyzer {

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {

        //Camera ekranındaki görüntüyü alıyoruz.
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            //Görüntü null değil ise onu Image'e cast ediyoruz.
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            //Barcode Tarayıcı Api'ye QR Kod formatında olanları işlemek istediğimizi söylüyoruz.
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE
                ).build()


            val scanner = BarcodeScanning.getClient(options)

            //Başarılı bir tarama sonrası qr içeriğini toasta basıyoruz.
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        Toast.makeText(
                            context,
                            "Value: " + barcode.rawValue,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener { }

        }

        imageProxy.close()
    }
}
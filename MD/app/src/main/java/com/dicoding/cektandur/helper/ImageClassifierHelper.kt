package com.dicoding.cektandur.helper

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.dicoding.cektandur.ml.FinalCektandur
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException

class ImageClassifierHelper(
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    private lateinit var model: FinalCektandur
    private val labels = listOf(
        "Anggur_Bercak_daun_isariopsis", "AnggurEsca(campak_hitam)", "AnggurHitam_busuk", "AnggurSehat",
        "ApelBusuk_hitam", "ApelKarat_apel_cedar", "ApelKeropeng_apel", "ApelSehat",
        "JagungBercak_daun_abu-abu", "JagungBusuk_daun", "JagungKarat_umum", "JagungSehat",
        "KentangBusuk_daun_dini", "KentangBusuk_daun_telat", "KentangSehat",
        "TomatBercak_bakteri", "TomatBercak_daun", "TomatBercak_target", "TomatBusuk_daun_dini",
        "TomatBusuk_daun_telat", "TomatDaun_keriting_kuning", "TomatJamur_septoria_lycopersici",
        "TomatSehat", "TomatTungau_laba-laba_Berbintik", "Tomat_Virus_mosaik_tomat"
    )

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        try {
            model = FinalCektandur.newInstance(context)
            Log.d(TAG, "Model initialized successfully")
        } catch (e: IOException) {
            classifierListener?.onError("Failed to initialize the model")
            Log.e(TAG, "Model initialization error: ${e.message}")
            e.printStackTrace()
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (bitmap == null) {
                classifierListener?.onError("Failed to decode the image")
                Log.e(TAG, "Bitmap is null")
                return
            }

            // Create a TensorImage and preprocess the image
            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)

            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(128, 128, ResizeOp.ResizeMethod.BILINEAR))
                .add(NormalizeOp(0f, 255f)) // Normalize to [0, 1]
                .build()

            val processedImage = imageProcessor.process(tensorImage)

            // Load the processed image buffer into TensorBuffer
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 128, 128, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(processedImage.buffer)

            // Perform inference
            val startTime = System.currentTimeMillis()
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val inferenceTime = System.currentTimeMillis() - startTime

            val outputArray = outputFeature0.floatArray
            Log.d(TAG, "Output Array: ${outputArray.joinToString()}")

            // Find the label with the highest confidence
            val maxIndex = outputArray.indices.maxByOrNull { outputArray[it] } ?: -1
            val predictedLabel = labels[maxIndex]
            val confidenceScore = outputArray[maxIndex]

            // Notify the listener with results
            classifierListener?.onResults(predictedLabel, confidenceScore, inferenceTime)
        } catch (e: IOException) {
            classifierListener?.onError("Failed to classify the image")
            Log.e(TAG, "Image classification error: ${e.message}")
            e.printStackTrace()
        } catch (e: Exception) {
            classifierListener?.onError("Failed to classify the image")
            Log.e(TAG, "Unexpected error: ${e.message}")
            e.printStackTrace()
        }
    }

    fun close() {
        model.close()
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            predictedLabel: String,
            confidenceScore: Float,
            inferenceTime: Long
        )
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
package com.dicoding.cektandur.helper

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
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
        1, 2, 3, 4, 5,
        6, 7, 8, 9, 10,
        11, 12, 13, 14, 15,
        16, 17, 18, 19, 20,
        21, 22, 23, 24, 25
    )

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        try {
            model = FinalCektandur.newInstance(context)
        } catch (e: IOException) {
            classifierListener?.onError("Failed to initialize the model")
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
                return
            }

            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)

            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(128, 128, ResizeOp.ResizeMethod.BILINEAR))
                .add(NormalizeOp(0f, 255f)) // Normalize to [0, 1]
                .build()

            val processedImage = imageProcessor.process(tensorImage)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 128, 128, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(processedImage.buffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val outputArray = outputFeature0.floatArray

            val maxIndex = outputArray.indices.maxByOrNull { outputArray[it] } ?: -1
            val predictedLabel = labels[maxIndex]
            val confidenceScore = outputArray[maxIndex]

            classifierListener?.onResults(predictedLabel, confidenceScore)
        } catch (e: IOException) {
            classifierListener?.onError("Failed to classify the image")
        } catch (e: Exception) {
            classifierListener?.onError("Failed to classify the image")
        }
    }

    fun close() {
        model.close()
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            predictedIdClass: Int,
            confidenceScore: Float
        )
    }
}
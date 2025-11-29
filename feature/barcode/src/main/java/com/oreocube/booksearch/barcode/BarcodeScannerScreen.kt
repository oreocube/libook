package com.oreocube.booksearch.barcode

import android.graphics.RectF
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.oreocube.booksearch.core.ui.component.LiBookTopBar
import com.oreocube.booksearch.core.ui.theme.LiBookPreviewTheme

@Composable
fun BookBarcodeScannerRoute(
    onBackClick: () -> Unit,
    onScanSuccess: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(onScanSuccess = onScanSuccess)
        GuideOverlay()
        LiBookTopBar(
            onNavigationIconClick = onBackClick,
            title = "도서 바코드 스캔"
        )
    }
}

@Composable
private fun CameraPreview(
    modifier: Modifier = Modifier,
    onScanSuccess: (String) -> Unit,
) {
    if (LocalInspectionMode.current) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
        )
        return
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = LifecycleCameraController(context).apply {
        setCameraSelector(CameraSelector.DEFAULT_BACK_CAMERA)
        bindToLifecycle(lifecycleOwner)
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            val previewView = PreviewView(context).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
                controller = cameraController
            }

            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_EAN_13)
                .build()
            val barcodeScanner = BarcodeScanning.getClient(options)

            cameraController.setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                MlKitAnalyzer(
                    listOf(barcodeScanner),
                    COORDINATE_SYSTEM_VIEW_REFERENCED,
                    ContextCompat.getMainExecutor(context)
                ) { result ->
                    val items = result?.getValue(barcodeScanner)
                    if (items.isNullOrEmpty()) return@MlKitAnalyzer

                    val item = items.first()
                    val guideRect = provideGuideRect(previewView)

                    item.boundingBox?.let { box ->
                        val cx = box.centerX().toFloat()
                        val cy = box.centerY().toFloat()

                        if (guideRect.contains(cx, cy)) {
                            cameraController.clearImageAnalysisAnalyzer()
                            onScanSuccess(item.rawValue.orEmpty())
                        }
                    }
                }
            )

            previewView
        }
    )
}

@Composable
private fun GuideOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawWithContent {
                drawRect(Color.Black.copy(alpha = 0.6f))

                val w = size.width * 0.7f
                val h = size.height * 0.2f

                val left = (size.width - w) / 2f
                val top = (size.height - h) / 2f
                val rect = Rect(left, top, left + w, top + h)

                val cornerRadius = 16f

                drawRoundRect(
                    color = Color.Transparent,
                    topLeft = rect.topLeft,
                    size = rect.size,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    blendMode = BlendMode.Clear,
                )

                drawRoundRect(
                    color = Color.White,
                    topLeft = rect.topLeft,
                    size = rect.size,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    style = Stroke(width = 3.dp.toPx())
                )

                drawContent()
            }
    ) {
        Text(
            "가이드라인 안에 바코드를 맞춰주세요",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
        )
    }
}

private fun provideGuideRect(previewView: PreviewView): RectF {
    val w = previewView.width.toFloat()
    val h = previewView.height.toFloat()

    return RectF(
        w * 0.15f,
        h * 0.40f,
        w * 0.85f,
        h * 0.60f
    )
}

@Composable
@Preview
private fun BarcodeScannerScreenPreview() {
    LiBookPreviewTheme {
        BookBarcodeScannerRoute(
            onBackClick = {},
            onScanSuccess = {},
        )
    }
}

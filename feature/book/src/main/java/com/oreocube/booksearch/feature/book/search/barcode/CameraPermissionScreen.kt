package com.oreocube.booksearch.feature.book.search.barcode

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.oreocube.booksearch.core.ui.theme.LiBookPreviewTheme

@Composable
fun CameraPermissionScreen(
    onPermissionGranted: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val permission = Manifest.permission.CAMERA

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) hasPermission = true
    }

    if (hasPermission) {
        onPermissionGranted()
        return
    }

    NoPermissionScreen(
        onPermissionRequestButtonClick = {
            permissionLauncher.launch(permission)
        }
    )
}

@Composable
private fun NoPermissionScreen(
    onPermissionRequestButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "도서 바코드를 스캔하려면 카메라 접근이 필요합니다.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        Button(onClick = onPermissionRequestButtonClick) {
            Text("권한 허용하기")
        }
    }
}

@Composable
@Preview
private fun NoPermissionScreenPreview() {
    LiBookPreviewTheme {
        NoPermissionScreen(
            onPermissionRequestButtonClick = {}
        )
    }
}

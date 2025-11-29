package com.oreocube.booksearch.barcode

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object BookBarcodeScannerRoute

fun NavController.navigateToBookBarcodeScanner(
    navOptions: NavOptions? = null,
) {
    navigate(route = BookBarcodeScannerRoute, navOptions)
}

fun NavGraphBuilder.bookBarcodeScannerScreen(
    onBackClick: () -> Unit,
    onScanSuccess: (String) -> Unit,
) {
    composable<BookBarcodeScannerRoute> {
        BookBarcodeScannerRoute(
            onBackClick = onBackClick,
            onScanSuccess = onScanSuccess,
        )
    }
}

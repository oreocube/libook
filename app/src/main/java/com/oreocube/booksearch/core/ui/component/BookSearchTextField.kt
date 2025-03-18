package com.oreocube.booksearch.core.ui.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.oreocube.booksearch.R
import com.oreocube.booksearch.core.ui.theme.Brown30
import com.oreocube.booksearch.core.ui.theme.Gray30

@Composable
fun BookSearchTextField(
    modifier: Modifier = Modifier,
    input: String,
    placeholder: String,
    onInputChanged: (String) -> Unit,
    onClearClicked: () -> Unit,
    onQuerySubmitted: () -> Unit,
) {
    TextField(
        modifier = modifier,
        value = input,
        onValueChange = onInputChanged,
        placeholder = { Text(text = placeholder) },
        trailingIcon = {
            if (input.isNotBlank())
                IconButton(onClick = onClearClicked) {
                    Icon(
                        painter = painterResource(R.drawable.ic_clear_24),
                        contentDescription = null,
                    )
                }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onQuerySubmitted() }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = BookSearchTextFieldDefaults.containerColor,
            errorContainerColor = BookSearchTextFieldDefaults.containerColor,
            disabledContainerColor = BookSearchTextFieldDefaults.containerColor,
            unfocusedContainerColor = BookSearchTextFieldDefaults.containerColor,
            focusedTextColor = BookSearchTextFieldDefaults.textColor,
            errorTextColor = BookSearchTextFieldDefaults.textColor,
            disabledTextColor = BookSearchTextFieldDefaults.textColor,
            unfocusedTextColor = BookSearchTextFieldDefaults.textColor,
            focusedPlaceholderColor = BookSearchTextFieldDefaults.placeholderColor,
            errorPlaceholderColor = BookSearchTextFieldDefaults.placeholderColor,
            disabledPlaceholderColor = BookSearchTextFieldDefaults.placeholderColor,
            unfocusedPlaceholderColor = BookSearchTextFieldDefaults.placeholderColor,
            focusedIndicatorColor = BookSearchTextFieldDefaults.indicatorColor,
            errorIndicatorColor = BookSearchTextFieldDefaults.indicatorColor,
            disabledIndicatorColor = BookSearchTextFieldDefaults.indicatorColor,
            unfocusedIndicatorColor = BookSearchTextFieldDefaults.indicatorColor,
            cursorColor = BookSearchTextFieldDefaults.cursorColor,
            errorCursorColor = BookSearchTextFieldDefaults.cursorColor,
            focusedTrailingIconColor = BookSearchTextFieldDefaults.trailingIconColor,
            errorTrailingIconColor = BookSearchTextFieldDefaults.trailingIconColor,
            disabledTrailingIconColor = BookSearchTextFieldDefaults.trailingIconColor,
            unfocusedTrailingIconColor = BookSearchTextFieldDefaults.trailingIconColor,
            selectionColors = TextSelectionColors(
                handleColor = Brown30,
                backgroundColor = Brown30.copy(alpha = 0.4f)
            ),
        )
    )
}

object BookSearchTextFieldDefaults {
    val containerColor = Color.White
    val textColor = Color.Black
    val trailingIconColor = Brown30
    val placeholderColor = Gray30
    val indicatorColor = Brown30
    val cursorColor = Brown30
}

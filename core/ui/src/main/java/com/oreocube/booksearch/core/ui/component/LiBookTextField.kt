package com.oreocube.booksearch.core.ui.component

import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.oreocube.booksearch.core.ui.R
import com.oreocube.booksearch.core.ui.theme.Brown30
import com.oreocube.booksearch.core.ui.theme.Gray30

@Composable
fun LiBookTextField(
    modifier: Modifier = Modifier,
    input: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onInputChanged: (String) -> Unit,
    onClearClicked: () -> Unit,
    onQuerySubmitted: () -> Unit,
) {
    TextField(
        modifier = modifier.height(72.dp),
        value = input,
        onValueChange = onInputChanged,
        placeholder = { Text(text = placeholder) },
        leadingIcon = leadingIcon,
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
            focusedContainerColor = LiBookTextFieldDefaults.containerColor,
            errorContainerColor = LiBookTextFieldDefaults.containerColor,
            disabledContainerColor = LiBookTextFieldDefaults.containerColor,
            unfocusedContainerColor = LiBookTextFieldDefaults.containerColor,
            focusedTextColor = LiBookTextFieldDefaults.textColor,
            errorTextColor = LiBookTextFieldDefaults.textColor,
            disabledTextColor = LiBookTextFieldDefaults.textColor,
            unfocusedTextColor = LiBookTextFieldDefaults.textColor,
            focusedPlaceholderColor = LiBookTextFieldDefaults.placeholderColor,
            errorPlaceholderColor = LiBookTextFieldDefaults.placeholderColor,
            disabledPlaceholderColor = LiBookTextFieldDefaults.placeholderColor,
            unfocusedPlaceholderColor = LiBookTextFieldDefaults.placeholderColor,
            focusedIndicatorColor = LiBookTextFieldDefaults.indicatorColor,
            errorIndicatorColor = LiBookTextFieldDefaults.indicatorColor,
            disabledIndicatorColor = LiBookTextFieldDefaults.indicatorColor,
            unfocusedIndicatorColor = LiBookTextFieldDefaults.indicatorColor,
            cursorColor = LiBookTextFieldDefaults.cursorColor,
            errorCursorColor = LiBookTextFieldDefaults.cursorColor,
            focusedTrailingIconColor = LiBookTextFieldDefaults.trailingIconColor,
            errorTrailingIconColor = LiBookTextFieldDefaults.trailingIconColor,
            disabledTrailingIconColor = LiBookTextFieldDefaults.trailingIconColor,
            unfocusedTrailingIconColor = LiBookTextFieldDefaults.trailingIconColor,
            selectionColors = TextSelectionColors(
                handleColor = Brown30,
                backgroundColor = Brown30.copy(alpha = 0.4f)
            ),
        )
    )
}

object LiBookTextFieldDefaults {
    val containerColor = Color.White
    val textColor = Color.Black
    val trailingIconColor = Brown30
    val placeholderColor = Gray30
    val indicatorColor = Brown30
    val cursorColor = Brown30
}

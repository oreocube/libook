package com.oreocube.booksearch.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oreocube.booksearch.R
import com.oreocube.booksearch.core.ui.theme.Brown20
import com.oreocube.booksearch.core.ui.theme.Gray20

@Composable
fun HomeRoute(
    onSearchBarClick: () -> Unit,
) {
    HomeScreen(
        onSearchBarClick = onSearchBarClick,
    )
}

@Composable
fun HomeScreen(
    onSearchBarClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.display_app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = Brown20,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.app_summary),
            style = MaterialTheme.typography.labelMedium,
            color = Gray20,
        )
        Spacer(modifier = Modifier.height(24.dp))
        LiBookSearchBar(
            onSearchBarClick = onSearchBarClick,
        )
    }
}

private val LiBookSearchBarMaxContentWidth = 600.dp
private val LiBookSearchBarHeight = 48.dp
private val LiBookSearchBarHorizontalPadding = 48.dp
private val LiBookSearchBarShape = RoundedCornerShape(32.dp)

@Composable
private fun LiBookSearchBar(
    onSearchBarClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = LiBookSearchBarMaxContentWidth)
            .padding(horizontal = LiBookSearchBarHorizontalPadding)
            .height(LiBookSearchBarHeight)
            .border(
                width = 1.dp,
                color = Brown20,
                shape = LiBookSearchBarShape
            )
            .clip(LiBookSearchBarShape)
            .background(Color.White)
            .clickable(onClick = onSearchBarClick)
            .padding(horizontal = 16.dp),
    ) {
        Icon(
            modifier = Modifier.align(Alignment.CenterEnd),
            painter = painterResource(R.drawable.ic_search_24),
            contentDescription = null,
            tint = Brown20,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview() {
    HomeScreen(onSearchBarClick = {})
}

package com.oreocube.booksearch.feature.region

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oreocube.booksearch.core.ui.theme.Brown10
import com.oreocube.booksearch.core.ui.theme.Brown60
import com.oreocube.booksearch.core.ui.theme.Grey80
import com.oreocube.booksearch.domain.model.City
import com.oreocube.booksearch.domain.model.District

@Composable
fun RegionScreen(
    selectedCityId: Int,
    selectedDistrictId: Int,
    cities: List<City>,
    districts: List<District>,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TableTitleItem(
                modifier = Modifier.weight(1f),
                text = "시·도",
            )
            VerticalDivider(
                modifier = Modifier.height(IntrinsicSize.Min),
            )
            TableTitleItem(
                modifier = Modifier.weight(1f),
                text = "시·군·구",
            )
        }

        RegionTable(
            modifier = Modifier.weight(1f),
            selectedCityId = selectedCityId,
            selectedDistrictId = selectedDistrictId,
            cities = cities,
            districts = districts,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = Brown10,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "도서관 찾기",
                textAlign = TextAlign.Center,
                color = Color.White,
            )
        }
    }
}

@Composable
private fun TableTitleItem(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier
            .background(color = Grey80)
            .padding(16.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            color = Color.Black,
        )
    }
}

@Composable
private fun RegionTable(
    modifier: Modifier = Modifier,
    selectedCityId: Int,
    selectedDistrictId: Int,
    cities: List<City>,
    districts: List<District>,
) {
    Row(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            cities.forEach { city ->
                item(key = city.id) {
                    RegionTableItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = city.name,
                        onClick = { TODO() },
                        isSelected = city.id == selectedCityId,
                        selectedTextColor = Color.White,
                        selectedBackgroundColor = Brown10,
                    )
                }
            }
        }
        VerticalDivider()
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            districts.forEach { district ->
                item(key = district.id) {
                    RegionTableItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = district.name,
                        onClick = { TODO() },
                        isSelected = district.id == selectedDistrictId,
                        selectedTextColor = Brown10,
                        selectedBackgroundColor = Brown60,
                    )
                }
            }
        }
    }
}

@Composable
private fun RegionTableItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean = false,
    defaultTextColor: Color = Color.DarkGray,
    selectedTextColor: Color,
    defaultBackgroundColor: Color = Color.Transparent,
    selectedBackgroundColor: Color,
) {
    Box(
        modifier = modifier
            .clickable(enabled = true, onClick = onClick)
            .background(
                color = if (isSelected) selectedBackgroundColor else defaultBackgroundColor
            )
            .padding(16.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            color = if (isSelected) selectedTextColor else defaultTextColor,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RegionScreenPreview() {
    RegionScreen(
        selectedCityId = 11,
        selectedDistrictId = 11002,
        cities = listOf(
            City(id = 11, name = "서울"),
            City(id = 21, name = "대구"),
        ),
        districts = listOf(
            District(id = 11001, cityId = 11, name = "강남구"),
            District(id = 11002, cityId = 11, name = "강동구"),
        )
    )
}

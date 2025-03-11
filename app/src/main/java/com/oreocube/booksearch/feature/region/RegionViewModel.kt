package com.oreocube.booksearch.feature.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oreocube.booksearch.domain.model.City
import com.oreocube.booksearch.domain.model.District
import com.oreocube.booksearch.domain.model.param.DistrictSearchParam
import com.oreocube.booksearch.domain.usecase.GetCitiesUseCase
import com.oreocube.booksearch.domain.usecase.GetDistrictsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getDistrictsUseCase: GetDistrictsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegionUiState>(RegionUiState.Loading)
    val uiState: StateFlow<RegionUiState> = _uiState.asStateFlow()

    init {
        getCities()
    }

    private fun getCities() {
        viewModelScope.launch {
            val cities = getCitiesUseCase()
            val selectedCityId = cities.firstOrNull()?.id ?: -1

            _uiState.value = RegionUiState.Table(
                selectedCityId = selectedCityId,
                cities = cities,
            )

            if (selectedCityId != -1) {
                onCitySelected(selectedCityId)
            }
        }
    }

    fun onCitySelected(id: Int) {
        viewModelScope.launch {
            val districts = getDistrictsUseCase(DistrictSearchParam(cityId = id))

            _uiState.update { state ->
                if (state is RegionUiState.Table) {
                    state.copy(
                        selectedCityId = id,
                        selectedDistrictId = -1,
                        districts = districts,
                    )
                } else {
                    state
                }
            }
        }
    }

    fun onDistrictSelected(id: Int) {
        _uiState.update { state ->
            if (state is RegionUiState.Table) {
                state.copy(selectedDistrictId = id)
            } else {
                state
            }
        }
    }
}

sealed class RegionUiState {
    data object Loading : RegionUiState()
    data class Table(
        val selectedCityId: Int,
        val selectedDistrictId: Int = -1,
        val cities: List<City>,
        val districts: List<District> = emptyList(),
    ) : RegionUiState()
}

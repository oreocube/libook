package com.oreocube.booksearch.feature.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oreocube.booksearch.domain.model.City
import com.oreocube.booksearch.domain.model.District
import com.oreocube.booksearch.domain.model.param.DistrictSearchParam
import com.oreocube.booksearch.domain.usecase.GetCitiesUseCase
import com.oreocube.booksearch.domain.usecase.GetDistrictsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _eventChannel = Channel<RegionUiEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    init {
        getCities()
    }

    private fun getCities() {
        viewModelScope.launch {
            runCatching {
                getCitiesUseCase()
            }.onSuccess { cities ->
                val selectedCityId = cities.firstOrNull()?.id ?: -1

                _uiState.value = RegionUiState.Table(
                    selectedCityId = selectedCityId,
                    cities = cities,
                )

                if (selectedCityId != -1) {
                    onCitySelected(selectedCityId)
                }
            }.onFailure {
                _eventChannel.send(RegionUiEvent.Error("데이터를 불러오지 못했습니다."))
            }
        }
    }

    fun onCitySelected(id: Int) {
        viewModelScope.launch {
            runCatching {
                getDistrictsUseCase(DistrictSearchParam(cityId = id))
            }.onSuccess { districts ->
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
            }.onFailure {
                _eventChannel.send(RegionUiEvent.Error("데이터를 불러오지 못했습니다."))
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

    fun onSearchButtonClicked(id: Int) {
        viewModelScope.launch {
            if (id == -1) {
                _eventChannel.send(RegionUiEvent.Error("지역을 선택해주세요."))
            } else {
                _eventChannel.send(RegionUiEvent.NavigateToSearchBook(id))
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

sealed class RegionUiEvent {
    data class NavigateToSearchBook(val districtId: Int) : RegionUiEvent()
    data class Error(val message: String) : RegionUiEvent()
}

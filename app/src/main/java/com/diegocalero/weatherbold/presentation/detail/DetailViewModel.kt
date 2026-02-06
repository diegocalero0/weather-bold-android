package com.diegocalero.weatherbold.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegocalero.weatherbold.core.network.Result
import com.diegocalero.weatherbold.core.ui.UiState
import com.diegocalero.weatherbold.domain.model.Forecast
import com.diegocalero.weatherbold.domain.usecase.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Forecast>>(UiState.Loading)
    val uiState: StateFlow<UiState<Forecast>> = _uiState.asStateFlow()

    private val _expandedDays = MutableStateFlow<Set<String>>(emptySet())
    val expandedDays: StateFlow<Set<String>> = _expandedDays.asStateFlow()

    init {
        val query = savedStateHandle.get<String>("query").orEmpty()
        loadForecast(query)
    }

    fun toggleDayExpanded(date: String) {
        _expandedDays.value = _expandedDays.value.toMutableSet().apply {
            if (contains(date)) remove(date) else add(date)
        }
    }

    fun retry(query: String) {
        loadForecast(query)
    }

    private fun loadForecast(query: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = getForecastUseCase(query)) {
                is Result.Success -> {
                    _uiState.value = UiState.Success(result.data)
                }
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.exception.message ?: "Unknown error")
                }
                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }
}
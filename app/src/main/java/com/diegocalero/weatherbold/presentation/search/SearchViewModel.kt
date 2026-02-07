package com.diegocalero.weatherbold.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegocalero.weatherbold.core.network.Result
import com.diegocalero.weatherbold.core.ui.UiState
import com.diegocalero.weatherbold.domain.model.Location
import com.diegocalero.weatherbold.domain.usecase.SearchLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val searchLocationsUseCase: SearchLocationsUseCase,
    ) : ViewModel() {
        private val _searchQuery = MutableStateFlow("")
        val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

        private val _uiState = MutableStateFlow<UiState<List<Location>>>(UiState.Idle)
        val uiState: StateFlow<UiState<List<Location>>> = _uiState.asStateFlow()

        init {
            observeSearchQuery()
        }

        fun onSearchQueryChanged(query: String) {
            _searchQuery.value = query
            if (query.isBlank()) {
                _uiState.value = UiState.Idle
            }
        }

        fun onClearSearch() {
            _searchQuery.value = ""
            _uiState.value = UiState.Idle
        }

        private fun observeSearchQuery() {
            _searchQuery
                .debounce(DEBOUNCE_DELAY)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .onEach { query -> searchLocations(query) }
                .launchIn(viewModelScope)
        }

        private fun searchLocations(query: String) {
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                when (val result = searchLocationsUseCase(query)) {
                    is Result.Success -> {
                        _uiState.value =
                            if (result.data.isEmpty()) {
                                UiState.Error("No locations found for \"$query\"")
                            } else {
                                UiState.Success(result.data)
                            }
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

        companion object {
            private const val DEBOUNCE_DELAY = 300L
        }
    }

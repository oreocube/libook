package com.oreocube.booksearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oreocube.booksearch.domain.usecase.AnonymousLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val anonymousLoginUseCase: AnonymousLoginUseCase,
) : ViewModel() {

    fun anonymousLogin() {
        viewModelScope.launch {
            runCatching {
                anonymousLoginUseCase()
            }
        }
    }
}

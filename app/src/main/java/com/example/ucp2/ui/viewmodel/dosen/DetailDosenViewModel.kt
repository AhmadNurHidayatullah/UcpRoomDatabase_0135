package com.example.ucp2.ui.viewmodel.dosen


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.repository.RepositoryDosen
import com.example.ucp2.ui.navigation.DestinasiDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailDosenViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {


    private val _nidn: String = checkNotNull(savedStateHandle[DestinasiDetail.NIDN])

    // Define the UI state flow for the Dosen detail
    val detailUiState: StateFlow<DetailUiState> = repositoryDosen.getDosen(_nidn)
        .filterNotNull()
        .map {
            // Map the Dosen data to DetailUiEvent
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true)) // Show loading state at first
            delay(600) // Optional delay for loading UI
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(isLoading = true)
        )


    fun insertDosen(dosen: Dosen) {
        viewModelScope.launch {
            try {
                repositoryDosen.insertDosen(dosen)
            } catch (e: Exception) {
            }
        }
    }
}



data class DetailUiState(
    val detailUiEvent: DosenEvent = DosenEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == DosenEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DosenEvent()
}

fun Dosen.toDetailUiEvent(): DosenEvent {
    return DosenEvent(
        NIDN = NIDN,
        Nama = Nama,
        JenisKelamin = JenisKelamin
    )
}

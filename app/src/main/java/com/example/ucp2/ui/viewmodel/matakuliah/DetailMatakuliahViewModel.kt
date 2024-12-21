package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.repository.RepositoryMK
import com.example.ucp2.ui.navigation.DestinasiDetailMK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class DetailMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMK: RepositoryMK,
) : ViewModel() {
    private val Kode: String = checkNotNull(savedStateHandle[DestinasiDetailMK.KODE])

    val detailUiState: StateFlow<DetailUiState> = repositoryMK.getMK(Kode)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiState = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue =
                DetailUiState(isLoading = true)
        )
}

data class DetailUiState(
    val detailUiState: MatakuliahEvent = MatakuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiState == MatakuliahEvent()

    val isUiEventNotEmpty: Boolean
        get()= detailUiState!= MatakuliahEvent()
}


fun Matakuliah.toDetailUiEvent(): MatakuliahEvent{
    return MatakuliahEvent(
        Kode = Kode,
        Nama = Nama,
        SKS = SKS,
        Semester = Semester,
        jenis = jenis,
        DosenPengampu = DosenPengampu
    )
}
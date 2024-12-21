package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.repository.RepositoryMK
import com.example.ucp2.ui.navigation.DestinasiUpdateMK
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMK: RepositoryMK
) : ViewModel(){

    var updateUiState by mutableStateOf(MkUiState())
        private set

    private val Kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMK.KODE])

    init {
        viewModelScope.launch {
            updateUiState = repositoryMK.getMK(Kode)
                .filterNotNull()
                .first()
                .toUiStateMK()
        }
    }
    fun updateState(matakuliahEvent: MatakuliahEvent){
        updateUiState = updateUiState.copy(
            matakuliahEvent = matakuliahEvent,
        )
    }
    fun validateFields(): Boolean{
        val event = updateUiState.matakuliahEvent
        val errorState = FormErrorState(
            Kode = if (event.Kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            Nama = if (event.Nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            SKS = if (event.SKS.isNotEmpty()) null else "SKS tidak boleh kosong",
            Semester = if (event.Semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "jenis tidak boleh kosong",
            DosenPengampu = if (event.DosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong",
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun updateData() {
        val currentEvent = updateUiState.matakuliahEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMK.updateMK(currentEvent.toMatakuliahEntity())
                    updateUiState = updateUiState.copy(
                        snackbarMessage = "Data Berhasil Disimpan",
                        matakuliahEvent = MatakuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUiState.snackbarMessage}")
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackbarMessage = "Data gagal Diupdate"
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(
                snackbarMessage = "Data gagal Diupdate"
            )

        }
    }
    fun resetSnackBarMessage(){
        updateUiState = updateUiState.copy(snackbarMessage = null)
    }
}

fun Matakuliah.toUiStateMK(): MkUiState = MkUiState(
    matakuliahEvent = this.toDetailUiEvent()
)
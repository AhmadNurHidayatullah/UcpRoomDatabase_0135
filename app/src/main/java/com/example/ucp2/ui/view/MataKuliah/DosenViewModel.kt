package com.example.ucp2.ui.view.MataKuliah

import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.repository.RepositoryDosen


data class DosenFormErrorState(
    val NIDN: String? = null,
    val Nama: String? = null,
    val JenisKelamin: String? = null
) {
    fun isValid(): Boolean {
        return NIDN == null && Nama == null && JenisKelamin == null
    }
}


private fun validateFields(): Boolean {
    val event = uiState.dosenEvent
    val errorState = DosenFormErrorState(
        NIDN = if (event.NIDN.isNotEmpty()) null else "NIDN tidak boleh kosong",
        Nama = if (event.Nama.isNotEmpty()) null else "Nama tidak boleh kosong",
        JenisKelamin = if (event.JenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
    )
    uiState = uiState.copy(isEntryValid = errorState)
    return errorState.isValid()
}

fun saveData() {
    val currentEvent = uiState.dosenEvent
    if (validateFields()) {
        viewModelScope.launch {
            try {
                RepositoryDosen.insertDosen(currentEvent.toDosenEntity())
                uiState = uiState.copy(
                    snackbarMessage = "Data berhasil disimpan",
                    dosenEvent = DosenEvent(), // Reset input form
                    isEntryValid = DosenFormErrorState() // reset error state
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    snackbarMessage = "Data gagal disimpan"
                )
            }
        }
    } else {
        uiState = uiState.copy(
            snackbarMessage = "Input tidak valid. periksa kembali data anda"
        )
    }
}

fun resetSnackBarMessage() {
    uiState = uiState.copy(snackbarMessage = null)
}



data class DosenUiState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: DosenFormErrorState = DosenFormErrorState(),
    val snackbarMessage: String? = null
)

data class DosenEvent(
    val NIDN: String = "",
    val Nama: String = "",
    val JenisKelamin: String = ""
)

fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    NIDN = NIDN,
    Nama = Nama,
    JenisKelamin = JenisKelamin
)
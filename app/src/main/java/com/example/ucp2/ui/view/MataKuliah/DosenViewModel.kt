package com.example.ucp2.ui.view.MataKuliah

import com.example.ucp2.data.entity.Dosen


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
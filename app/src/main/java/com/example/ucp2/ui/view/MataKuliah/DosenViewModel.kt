package com.example.ucp2.ui.view.MataKuliah


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
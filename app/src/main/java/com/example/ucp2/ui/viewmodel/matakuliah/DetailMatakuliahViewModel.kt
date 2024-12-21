package com.example.ucp2.ui.viewmodel.matakuliah

import com.example.ucp2.data.entity.Matakuliah

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
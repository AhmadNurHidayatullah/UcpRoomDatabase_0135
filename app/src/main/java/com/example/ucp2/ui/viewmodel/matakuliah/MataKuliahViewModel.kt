package com.example.ucp2.ui.viewmodel.matakuliah

import com.example.ucp2.data.entity.Matakuliah



data class MkUiState(
    val matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null
)


data class FormErrorState(
    val Kode: String? = null,
    val Nama: String? = null,
    val SKS: String? = null,
    val Semester: String? = null,
    val jenis: String? = null,
    val DosenPengampu: String? = null
) {
    fun isValid(): Boolean {
        return Kode == null && Nama == null && SKS == null &&
                Semester == null && jenis == null && DosenPengampu == null
    }
}

fun MatakuliahEvent.toMatakuliahEntity(): Matakuliah = Matakuliah (
    Kode = Kode,
    Nama = Nama,
    SKS = SKS,
    Semester = Semester,
    jenis = jenis,
    DosenPengampu = DosenPengampu
)

data class MatakuliahEvent(
    val Kode: String = "",
    val Nama: String = "",
    val SKS: String = "",
    val Semester: String = "",
    val jenis: String = "",
    val DosenPengampu: String = ""
)
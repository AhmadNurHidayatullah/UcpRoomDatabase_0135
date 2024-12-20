package com.example.ucp2.ui.viewmodel.matakuliah

import com.example.ucp2.data.entity.Matakuliah


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
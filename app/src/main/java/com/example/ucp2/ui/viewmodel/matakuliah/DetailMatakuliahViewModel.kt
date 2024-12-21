package com.example.ucp2.ui.viewmodel.matakuliah

import com.example.ucp2.data.entity.Matakuliah

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
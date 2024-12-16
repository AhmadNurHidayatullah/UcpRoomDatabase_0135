package com.example.ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matakuliah")
data class Matakuliah(
    @PrimaryKey
    val Kode: String,
    val Nama: String,
    val SKS: String,
    val Semester: String,
    val jenis: String,
    val DosenPengampu: String
)

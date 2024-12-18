package com.example.ucp2.repository

import androidx.room.Update
import com.example.ucp2.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMK {
    suspend fun insertMK(matakuliah: Matakuliah)
    fun getAllMatakuliah(): Flow<List<Matakuliah>>

    fun getMK(kode: String): Flow<Matakuliah>

    suspend fun deleteMK(matakuliah: Matakuliah)

    suspend fun UpdateMK(matakuliah: Matakuliah)
}
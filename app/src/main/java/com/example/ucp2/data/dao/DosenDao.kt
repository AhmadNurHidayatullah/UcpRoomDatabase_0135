package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {
    @Query("select * from dosen")
    fun getAllDosen() : Flow<List<Dosen>>

    @Query("select * from dosen where NIDN = :NIDN")
    fun getDosen(NIDN: String): Flow<Dosen>

    @Insert
    suspend fun insertDosen(dosen: Dosen )

}
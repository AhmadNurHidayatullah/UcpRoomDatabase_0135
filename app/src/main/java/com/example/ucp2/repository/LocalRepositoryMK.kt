package com.example.ucp2.repository

import com.example.ucp2.data.dao.MatakuliahDao
import com.example.ucp2.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMK (
    private val matakuliahDao: MatakuliahDao
): RepositoryMK {
    override suspend fun insertMK(matakuliah: Matakuliah) {
        matakuliahDao.insertMatakuliah(matakuliah)
    }

    override fun getAllMatakuliah(): Flow<List<Matakuliah>> {
        return matakuliahDao.getAllMatakuliah()
    }

    override fun getMK(kode: String): Flow<Matakuliah> {
        return matakuliahDao.getMatakuliah(kode)
    }

    override suspend fun deleteMK(matakuliah: Matakuliah) {
        return matakuliahDao.deleteMatakuliah(matakuliah)
    }

    override suspend fun updateMK(matakuliah: Matakuliah) {
        matakuliahDao.updateMatakuliah(matakuliah)
    }
}
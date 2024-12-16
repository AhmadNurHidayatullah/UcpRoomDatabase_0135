package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.dao.MatakuliahDao
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.Matakuliah

@Database(entities = [Dosen::class, Matakuliah::class], version = 1, exportSchema = false)
abstract class ProdiDatabase : RoomDatabase(){
    abstract fun dosenDao():DosenDao
    abstract fun matakuliahDao():MatakuliahDao

    companion object{
        @Volatile
        private var Instance: ProdiDatabase? = null

        fun getDatabase(context: Context): ProdiDatabase{
            return (Instance?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    ProdiDatabase::class.java,
                    "ProdiDatabase"
                )
                    .build().also { Instance = it }
            })
        }
    }
}
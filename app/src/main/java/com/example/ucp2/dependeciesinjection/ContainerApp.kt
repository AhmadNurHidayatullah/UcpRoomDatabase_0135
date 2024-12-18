package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.repository.LocalRepositoryDosen
import com.example.ucp2.repository.LocalRepositoryMK
import com.example.ucp2.repository.RepositoryDosen
import com.example.ucp2.repository.RepositoryMK
import com.example.ucp2.data.database.ProdiDatabase

interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
    val repositoryMK: RepositoryMK
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    private val database by lazy { ProdiDatabase.getDatabase(context) }

    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoryDosen(database.dosenDao())
    }

    override val repositoryMK: RepositoryMK by lazy {
        LocalRepositoryMK(database.matakuliahDao())
    }
}

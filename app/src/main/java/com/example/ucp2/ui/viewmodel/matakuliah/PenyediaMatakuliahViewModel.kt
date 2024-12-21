package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.ProdiApp
import com.example.ucp2.ui.viewmodel.dosen.ProdiApp

object PenyediaMatakuliahViewModel{

    val Factory = viewModelFactory {
        initializer {
            MataKuliahViewModel(
                ProdiApp().containerApp.repositoryMK
            )
        }
        initializer {
            HomeMatakuliahViewModel(
                ProdiApp().containerApp.repositoryMK
            )
        }
        initializer {
            DetailMatakuliahViewModel(
            createSavedStateHandle(),
            ProdiApp().containerApp.repositoryMK
            )
        }
        initializer {
            UpdateMatakuliahViewModel(
                createSavedStateHandle(),
                ProdiApp().containerApp.repositoryMK
            )
        }
    }
}

fun CreationExtras.ProdiApp(): ProdiApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProdiApp)
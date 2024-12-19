package com.example.ucp2.ui.viewmodel.dosen

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.ProdiApp

object PenyediaDosenViewModel{

    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                ProdiApp().containerApp.repositoryDosen,
            )
        }
        initializer {
            HomeDosenViewModel(
                ProdiApp().containerApp.repositoryDosen,
            )
        }
        initializer {
            DetailDosenViewModel(
                createSavedStateHandle(),
                ProdiApp().containerApp.repositoryDosen,
            )
        }
    }
}

fun CreationExtras.ProdiApp(): ProdiApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProdiApp)
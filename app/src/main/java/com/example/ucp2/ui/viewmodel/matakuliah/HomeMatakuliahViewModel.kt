package com.example.ucp2.ui.viewmodel.matakuliah


import com.example.ucp2.data.entity.Matakuliah

data class HomeUiState(
    val listDosen: List<Matakuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
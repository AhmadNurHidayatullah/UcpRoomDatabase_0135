package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.repository.RepositoryMK
import kotlinx.coroutines.launch

class MataKuliahViewModel(private val repositoryMK: RepositoryMK): ViewModel(){

    var uiState by mutableStateOf(MkUiState())

    fun UpdateState(matakuliahEvent: MatakuliahEvent){
        uiState = uiState.copy(
            matakuliahEvent = matakuliahEvent
        )
    }

    private fun validateFields(): Boolean{
        val event = uiState.matakuliahEvent
        val errorState = FormErrorState(
            Nama = if (event.Nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            Kode = if (event.Kode.isNotEmpty()) null else "Nama tidak boleh kosong",
            SKS = if (event.SKS.isNotEmpty()) null else "Nama tidak boleh kosong",
            Semester = if (event.Semester.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Nama tidak boleh kosong",
            DosenPengampu = if (event.DosenPengampu.isNotEmpty()) null else "Nama tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun saveData(){
        val currentEvent = uiState.matakuliahEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMK.insertMK(currentEvent.toMatakuliahEntity())
                    uiState = uiState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        matakuliahEvent = MatakuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception){
                    uiState = uiState.copy(
                        snackbarMessage = "Data gagal disimpan"
                    )
                }
            }
        }else{
            uiState = uiState.copy(
                snackbarMessage = "Input tidak valid. periksa kembali data anda"
            )
        }
    }
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackbarMessage = null)
    }
}

data class MkUiState(
    val matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null
)


data class FormErrorState(
    val Kode: String? = null,
    val Nama: String? = null,
    val SKS: String? = null,
    val Semester: String? = null,
    val jenis: String? = null,
    val DosenPengampu: String? = null
) {
    fun isValid(): Boolean {
        return Kode == null && Nama == null && SKS == null &&
                Semester == null && jenis == null && DosenPengampu == null
    }
}

fun MatakuliahEvent.toMatakuliahEntity(): Matakuliah = Matakuliah (
    Kode = Kode,
    Nama = Nama,
    SKS = SKS,
    Semester = Semester,
    jenis = jenis,
    DosenPengampu = DosenPengampu
)

data class MatakuliahEvent(
    val Kode: String = "",
    val Nama: String = "",
    val SKS: String = "",
    val Semester: String = "",
    val jenis: String = "",
    val DosenPengampu: String = ""
)
package com.example.ucp2.ui.view.MataKuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.repository.RepositoryDosen
import kotlinx.coroutines.launch

class DosenViewModel(private val repositoryDosen: RepositoryDosen): ViewModel() {

    var uiState by mutableStateOf(DosenUiState())

    // Update the UI state based on DosenEvent input
    fun updateState(dosenEvent: DosenEvent) {
        uiState = uiState.copy(
            dosenEvent = dosenEvent
        )
    }

    // Validate the input fields
    private fun validateFields(): Boolean {
        val event = uiState.dosenEvent
        val errorState = DosenFormErrorState(
            NIDN = if (event.NIDN.isNotEmpty()) null else "NIDN tidak boleh kosong",
            Nama = if (event.Nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            JenisKelamin = if (event.JenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Save data to the repository
    fun saveData() {
        val currentEvent = uiState.dosenEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDosen.insertDosen(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        dosenEvent = DosenEvent(), // Reset input form
                        isEntryValid = DosenFormErrorState() // reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackbarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackbarMessage = "Input tidak valid. periksa kembali data anda"
            )
        }
    }

    // Reset Snackbar message after it is displayed
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackbarMessage = null)
    }
}

// State for managing Dosen UI
data class DosenUiState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: DosenFormErrorState = DosenFormErrorState(),
    val snackbarMessage: String? = null
)

// Validation state for Dosen
data class DosenFormErrorState(
    val NIDN: String? = null,
    val Nama: String? = null,
    val JenisKelamin: String? = null
) {
    fun isValid(): Boolean {
        return NIDN == null && Nama == null && JenisKelamin == null
    }
}

// Convert DosenEvent to Dosen entity for database insertion
fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    NIDN = NIDN,
    Nama = Nama,
    JenisKelamin = JenisKelamin
)

// Data class to represent the form input for Dosen
data class DosenEvent(
    val NIDN: String = "",
    val Nama: String = "",
    val JenisKelamin: String = ""
)
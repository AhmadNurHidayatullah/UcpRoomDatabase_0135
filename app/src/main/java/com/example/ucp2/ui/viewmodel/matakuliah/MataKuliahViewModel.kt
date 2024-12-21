package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.repository.RepositoryMK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MataKuliahViewModel(private val repositoryMK: RepositoryMK): ViewModel() {
    // Maintain UI state using mutableStateOf for simple cases
    var uiState by mutableStateOf(MkUiState())
        private set

    // Home UI state using MutableStateFlow for more complex state handling
    private val _homeMkUiState = MutableStateFlow(HomeMkUiState())
    val homeMkUiState: StateFlow<HomeMkUiState> = _homeMkUiState.asStateFlow()

    init {
        listMatakuliah()
    }

    // Fetch list of courses from the repository
    private fun listMatakuliah() {
        viewModelScope.launch {
            repositoryMK.getAllMatakuliah()
                .catch { exception ->
                    _homeMkUiState.value = HomeMkUiState(
                        isError = true,
                        errorMessage = exception.message ?: "Unknown Error"
                    )
                }
                .collect { listMk ->
                    _homeMkUiState.value = HomeMkUiState(listMk = listMk)
                }
        }
    }

    // Update the state based on user input
    fun updateState(matakuliahEvent: MatakuliahEvent) {
        uiState = uiState.copy(matakuliahEvent = matakuliahEvent)
    }

    // Validate input fields before saving
    private fun validateFields(): Boolean {
        val event = uiState.matakuliahEvent
        val errorState = FormErrorState(
            Nama = if (event.Nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            Kode = if (event.Kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            SKS = if (event.SKS.isNotEmpty()) null else "SKS tidak boleh kosong",
            Semester = if (event.Semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            DosenPengampu = if (event.DosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Save the data to the repository
    fun saveData() {
        val currentEvent = uiState.matakuliahEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMK.insertMK(currentEvent.toMatakuliahEntity())
                    uiState = uiState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        matakuliahEvent = MatakuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(snackbarMessage = "Data gagal disimpan")
                }
            }
        } else {
            uiState = uiState.copy(snackbarMessage = "Input tidak valid. Periksa kembali data anda")
        }
    }

    // Reset snackbar message
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackbarMessage = null)
    }
}

// UI State to represent form data and validation state
data class MkUiState(
    val matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null
)

// Form error state that tracks validation errors
data class FormErrorState(
    val Kode: String? = null,
    val Nama: String? = null,
    val SKS: String? = null,
    val Semester: String? = null,
    val jenis: String? = null,
    val DosenPengampu: String? = null
) {
    // Check if all fields are valid
    fun isValid(): Boolean {
        return Kode == null && Nama == null && SKS == null &&
                Semester == null && jenis == null && DosenPengampu == null
    }
}

// Data class representing the event data (form input)
data class MatakuliahEvent(
    val Kode: String = "",
    val Nama: String = "",
    val SKS: String = "",
    val Semester: String = "",
    val jenis: String = "",
    val DosenPengampu: String = ""
)

// Convert MatakuliahEvent to Matakuliah entity for repository
fun MatakuliahEvent.toMatakuliahEntity(): Matakuliah = Matakuliah(
    Kode = Kode,
    Nama = Nama,
    SKS = SKS,
    Semester = Semester,
    jenis = jenis,
    DosenPengampu = DosenPengampu
)


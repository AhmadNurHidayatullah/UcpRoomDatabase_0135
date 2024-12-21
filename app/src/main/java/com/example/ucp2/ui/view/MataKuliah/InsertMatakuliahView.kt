package com.example.ucp2.ui.view.MataKuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.costumwidget.DynamicDropdownTextField
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.dosen.HomeDosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.HomeUiState
import com.example.ucp2.ui.viewmodel.dosen.PenyediaDosenViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.FormErrorState
import com.example.ucp2.ui.viewmodel.matakuliah.MataKuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahEvent
import com.example.ucp2.ui.viewmodel.matakuliah.MkUiState
import com.example.ucp2.ui.viewmodel.matakuliah.PenyediaMatakuliahViewModel
import kotlinx.coroutines.launch

object DestinasiInsertMatakuliah : AlamatNavigasi {
    override val route = "TambahMataKuliah"
}

@Composable
fun InsertMatakuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaMatakuliahViewModel.Factory),
    viewModelDsn: HomeDosenViewModel = viewModel(factory = PenyediaDosenViewModel.Factory),
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val dsnList by viewModelDsn.homeUiState.collectAsState()

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Matakuliah"
            )
            InsertBodyMatakuliah(
                uiState = uiState,
                listDosen = dsnList,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}


@Composable
fun InsertBodyMatakuliah(
    modifier: Modifier = Modifier,
    onValueChange: (MatakuliahEvent) -> Unit,
    onClick: () -> Unit,
    uiState: MkUiState,
    listDosen: HomeUiState
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            matakuliahEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            listDsn = listDosen.listDosen
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}


@Composable
fun FormMatakuliah(
    matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    onValueChange: (MatakuliahEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    listDsn: List<Dosen>
) {
    val sks = listOf("1", "2", "3", "4", "5", "6")
    val jenis = listOf("Wajib", "Peminatan")
    val namaDosenList = listDsn.map { it.Nama }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.Kode,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(Kode = it))
            },
            label = { Text("Kode") },
            isError = errorState.Kode != null,
            placeholder = { Text("Masukkan Kode") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.Kode ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.Nama,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(Nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.Nama != null,
            placeholder = { Text("Masukkan Nama") },
        )
        Text(
            text = errorState.Nama ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "SKS")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            sks.forEach { sksOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.SKS == sksOption,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(SKS = sksOption))
                        },
                    )
                    Text(text = sksOption)
                }
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.Semester,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(Semester = it))
            },
            label = { Text("Semester") },
            isError = errorState.Semester != null,
            placeholder = { Text("Masukkan Semester") },
        )
        Text(
            text = errorState.Semester ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Jenis")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenis.forEach { jenisOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.jenis == jenisOption,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(jenis = jenisOption))
                        },
                    )
                    Text(text = jenisOption)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Dosen Pengampu")
        DynamicDropdownTextField(
            selectedValue = matakuliahEvent.DosenPengampu,
            options = namaDosenList,
            label = "Pilih Dosen Pengampu",
            onValueChanged = {
                onValueChange(matakuliahEvent.copy(DosenPengampu = it))
            }
        )
        Text(
            text = errorState.DosenPengampu ?: "",
            color = Color.Red
        )
    }
}



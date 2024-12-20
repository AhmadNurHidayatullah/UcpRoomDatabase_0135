package com.example.ucp2.ui.view.Dosen

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.dosen.DosenEvent
import com.example.ucp2.ui.viewmodel.dosen.DosenFormErrorState
import com.example.ucp2.ui.viewmodel.dosen.DosenUiState
import com.example.ucp2.ui.viewmodel.dosen.DosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.PenyediaDosenViewModel
import kotlinx.coroutines.launch


object DestinasiInsertDosen : AlamatNavigasi{
    override val route = "TambahDosen"
}

@Composable
fun InsertDosenView(
    onBack:()->Unit,
    onNavigate:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: DosenViewModel = viewModel(factory = PenyediaDosenViewModel.Factory)
){
    val uiState = viewModel.uiState
    val snackbarHostState = remember{ SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold (
        modifier=modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Dosen",
                modifier = Modifier
            )
            InsertBodyDosen(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent)
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
fun InsertBodyDosen(
    modifier: Modifier = Modifier,
    onValueChange:(DosenEvent) -> Unit,
    onClick: () -> Unit,
    uiState: DosenUiState
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormDosen(
            dosenEvent = uiState.dosenEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormDosen(
    dosenEvent: DosenEvent = DosenEvent(),
    onValueChange: (DosenEvent) -> Unit = {},
    errorState: DosenFormErrorState = DosenFormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenisKelamin = listOf("Laki-Laki", "Perempuan")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.Nama,
            onValueChange = {
                onValueChange(dosenEvent.copy(Nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.Nama != null,
            placeholder = { Text("Masukkan Nama") },
        )
        Text(
            text = errorState.Nama ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.NIDN,
            onValueChange = {
                onValueChange(dosenEvent.copy(NIDN = it))
            },
            label = { Text("NIDN") },
            isError = errorState.NIDN != null,
            placeholder = { Text("Masukkan NIDN") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.NIDN ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Jenis Kelamin")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenisKelamin.forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = dosenEvent.JenisKelamin == jk,
                        onClick = {
                            onValueChange(dosenEvent.copy(JenisKelamin = jk))
                        },
                    )
                    Text(text = jk)
                }
            }
        }
    }
}

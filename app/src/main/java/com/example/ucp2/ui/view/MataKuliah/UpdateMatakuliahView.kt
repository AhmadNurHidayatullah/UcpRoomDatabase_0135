package com.example.ucp2.ui.view.MataKuliah

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.viewmodel.dosen.HomeDosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.PenyediaDosenViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.PenyediaMatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.UpdateMatakuliahViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMatakuliahView(
    onback: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMatakuliahViewModel = viewModel(factory = PenyediaMatakuliahViewModel.Factory),
    viewModelDosenViewModel: HomeDosenViewModel = viewModel (factory = PenyediaDosenViewModel.Factory)
) {
    val uiState = viewModel.updateUiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val DosenList by viewModelDosenViewModel.homeUiState.collectAsState()

    LaunchedEffect (uiState.snackbarMessage){
        println("LaunchedEffect triggered")
        uiState.snackbarMessage?.let { message ->
            println("Snackbar message recieved: $message")
            coroutineScope.launch {
                println("Launching corotine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            com.example.ucp2.ui.costumwidget.TopAppBar(
                judul = "Edit Matakuliah",
                showBackButton = true,
                onBack = onback
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ){
            InsertBodyMatakuliah(
                uiState = uiState,
                listDosen = DosenList,
                onValueChange = {updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            withContext(Dispatchers.Main) {
                                onNavigate
                            }
                        }
                    }
                }
            )
        }
    }
}
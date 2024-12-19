package com.example.appmahasiswa.ui.view.dosen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.dosen.DetailDosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.DetailUiState
import com.example.ucp2.ui.viewmodel.dosen.toDosenEntity

@Preview(showBackground = true)
@Composable
fun DetailDosenView(
    modifier: Modifier = Modifier,
    viewModel: DetailDosenViewModel = viewModel(),
    onBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Detail Dosen",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
    ) { innerPadding ->
        val detailDosenUiState by viewModel.detailUiState.collectAsState()

        BodyDetailDosen(
            modifier = Modifier.padding(innerPadding),
            detailDosenUiState = detailDosenUiState
        )
    }
}

@Composable
fun BodyDetailDosen(
    modifier: Modifier = Modifier,
    detailDosenUiState: DetailUiState = DetailUiState(),
) {
    when {
        detailDosenUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        detailDosenUiState.isError -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Failed to load data. Please try again.",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        detailDosenUiState.detailUiEvent != null -> {
            // Show existing data if available
            DataDetailDosen(
                modifier = modifier,
                dosen = detailDosenUiState.detailUiEvent.toDosenEntity()
            )
        }

        else -> {
            // No data found
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Tidak Ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun DataDetailDosen(
    modifier: Modifier = Modifier,
    dosen: Dosen
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ItemDetailDosen(judul = "NIDN", isinya = dosen.NIDN)
            Spacer(modifier = Modifier.padding(4.dp))
            ItemDetailDosen(judul = "Nama", isinya = dosen.Nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ItemDetailDosen(judul = "Jenis Kelamin", isinya = dosen.JenisKelamin)
        }
    }
}

@Composable
fun ItemDetailDosen(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String // Dosen data content
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya, fontSize = 20.sp, // Display the content
            fontWeight = FontWeight.Bold
        )
    }
}


package com.example.appmahasiswa.ui.view.dosen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.viewmodel.dosen.DetailUiState
import com.example.ucp2.ui.viewmodel.dosen.DosenUiState
import com.example.ucp2.ui.viewmodel.dosen.toDosenEntity


@Preview(showBackground = true)
@Composable
fun BodyDetailDosen(
    modifier: Modifier = Modifier,
    DetailDosenUiState: DetailUiState = DetailUiState(),
) {
    // state for showing the insert confirmation dialog
    var insertConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when {
        DetailDosenUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Show loading indicator
            }
        }

        DetailDosenUiState.isError -> {
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
        else -> {
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
            itemDetailDosen(judul = "NIDN", isinya = dosen.NIDN)
            Spacer(modifier = Modifier.padding(4.dp))
            itemDetailDosen(judul = "Nama", isinya = dosen.Nama)
            Spacer(modifier = Modifier.padding(4.dp))
            itemDetailDosen(judul = "Jenis Kelamin", isinya = dosen.JenisKelamin)
        }
    }
}


@Composable
fun itemDetailDosen(
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
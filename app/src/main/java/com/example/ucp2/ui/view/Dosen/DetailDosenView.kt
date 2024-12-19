package com.example.appmahasiswa.ui.view.dosen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.data.entity.Dosen


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
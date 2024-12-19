package com.example.ucp2.ui.view.HomeView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeView(
    onNavigateToDosen: () -> Unit,
    onNavigateToMatakuliah: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Welcome Text
        Text(
            text = "Selamat Datang!",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Card for Dosen Button
        NavigationCard(
            text = "Halaman Dosen",
            icon = Icons.Filled.Person,
            iconColor = Color.Black,
            backgroundColor = Color.Cyan,
            onClick = onNavigateToDosen
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card for Matakuliah Button
        NavigationCard(
            text = "Halaman Matakuliah",
            icon = Icons.Filled.AccountBox,
            iconColor = Color.Blue,
            backgroundColor = Color.Cyan,
            onClick = onNavigateToMatakuliah
        )
    }
}



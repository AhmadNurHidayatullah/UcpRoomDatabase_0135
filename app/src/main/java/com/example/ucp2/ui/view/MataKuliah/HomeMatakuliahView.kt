package com.example.ucp2.ui.view.MataKuliah

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ucp2.data.entity.Matakuliah

@Composable
fun ListMK(
    listMK: List<Matakuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    LazyColumn (
        modifier = Modifier
    ){
        items(
            items = listMK,
            itemContent = { MK ->
                CardMK(
                    MK = MK,
                    onClick = { onClick(MK.Kode)}
                )
            }
        )
    }
}

@Composable
fun CardMK(
    MK: Matakuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
){}

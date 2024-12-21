package com.example.ucp2.ui.view.MataKuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.costumwidget.DynamicDropdownTextField
import com.example.ucp2.ui.viewmodel.matakuliah.FormErrorState
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahEvent




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



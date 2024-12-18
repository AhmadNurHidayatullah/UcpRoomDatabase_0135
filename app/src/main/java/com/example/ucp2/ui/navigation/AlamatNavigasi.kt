package com.example.ucp2.ui.navigation

interface AlamatNavigasi{
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}

object DestinasiDosen : AlamatNavigasi {
    override val route: String = "Dosen"
}

object DestinasiInsertDosen : AlamatNavigasi{
    override val route = "Tambah Dosen"
}

object DestinasiMatakuliah : AlamatNavigasi{
    override val route = "MataKuliah"
}

object DestinasiInsertMatakuliah : AlamatNavigasi{
    override val route = "Tambah MataKuliah"
}

object DestinasiDetail : AlamatNavigasi{
    override val route = "Detail MataKuliah"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdate : AlamatNavigasi{
    override val route = "Update MataKuliah"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}


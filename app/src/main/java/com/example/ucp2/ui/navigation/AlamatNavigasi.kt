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

object DestinasiDetail : AlamatNavigasi{
    override val route = "DosenDetail"
    const val NIDN = "NIDN"
    val routeWithArg = "$route/{$NIDN}"
}

object DestinasiMatakuliah : AlamatNavigasi{
    override val route = "MataKuliah"
}

object DestinasiInsertMatakuliah : AlamatNavigasi{
    override val route = "Tambah MataKuliah"
}

object DestinasiDetailMK : AlamatNavigasi{
    override val route = "Detail MataKuliah"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMK : AlamatNavigasi{
    override val route = "Update MataKuliah"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}


package com.example.pertemuan13.model

class Mahasiswa (
    val nim: String,
    val nama: String,
    val alamat: String,
    val jeniskelamin: String,
    val kelas: String,
    val angkatan: String

){
    constructor(): this("","","","","","")
}

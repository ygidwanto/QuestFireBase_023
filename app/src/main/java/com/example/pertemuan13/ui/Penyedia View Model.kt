package com.example.pertemuan13.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan13.MahasiswaApplications
import com.example.pertemuan13.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.channels.Channel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(AplikasiMahasiswa().container.mahasiswaRepository) }

    }
}

fun CreationExtras.AplikasiMahasiswa(): MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)
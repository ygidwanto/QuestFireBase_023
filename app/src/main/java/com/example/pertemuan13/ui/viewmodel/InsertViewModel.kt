package com.example.pertemuan13.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.repository.RepositoryMhs
import com.google.firebase.events.Event

class InsertViewModel(
    private val mhs: RepositoryMhs
): ViewModel(){
    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set
    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set
    // Memperbarui state berdasarkan input pengguna
    fun updateState(mahasiswaEvent: MahasiswaEvent){
        uiEvent = uiEvent.copy(
            insertUiEvent = mahasiswaEvent,
        )
    }
}
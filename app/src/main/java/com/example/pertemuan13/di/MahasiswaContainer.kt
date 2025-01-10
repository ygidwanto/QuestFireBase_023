package com.example.pertemuan13.di

import com.example.pertemuan13.repository.NetworkRepositoryMhs
import com.example.pertemuan13.repository.RepositoryMhs
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer{
    val mahasiswaRepository: RepositoryMhs
}

class MahasiswaContainer: AppContainer{

    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()

    override val mahasiswaRepository: RepositoryMhs by lazy {
        NetworkRepositoryMhs(firebase)
    }
}
package com.example.pertemuan13.repository

import android.R.attr.value
import com.example.pertemuan13.model.Mahasiswa
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface RepositoryMhs {

        suspend fun insertMhs(mahasiswa: Mahasiswa)
        fun getAllMhs(): Flow<List<Mahasiswa>>
        fun getMhs (nim: String): Flow<Mahasiswa>
        suspend fun deleteMhs(mahasiswa: Mahasiswa)
        suspend fun updateMhs(mahasiswa: Mahasiswa)

}


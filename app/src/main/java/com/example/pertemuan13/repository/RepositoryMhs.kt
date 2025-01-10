package com.example.pertemuan13.repository

import com.example.pertemuan13.model.Mahasiswa
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

interface RepositoryMhs {

        suspend fun insertMhs(mahasiswa: Mahasiswa)
        fun getAllMhs(): Flow<List<Mahasiswa>>
        fun getMhs (nim: String): Flow<Mahasiswa>
        suspend fun deleteMhs(mahasiswa: Mahasiswa)
        suspend fun updateMhs(mahasiswa: Mahasiswa)

}

class NetworkRepositoryMhs(
        private val firestore: FirebaseFirestore
) : RepositoryMhs{
        override suspend fun insertMhs(mahasiswa: Mahasiswa) {
            try {
                firestore.collection("Mahasiswa").add(mahasiswa).await()
            } catch (e: Exception){
                    throw Exception("Gagal menambahkan data mahasiswa: " +
                            "${e.message}")
            }
        }
}
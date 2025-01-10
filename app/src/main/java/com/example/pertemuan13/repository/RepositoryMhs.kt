package com.example.pertemuan13.repository

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

        override fun getAllMhs(): Flow<List<Mahasiswa>> = callbackFlow {
                val mhsCollection = firestore.collection("Mahasiswa")
                        .orderBy("nim", Query.Direction.DESCENDING)
                        .addSnapshotListener{value, error ->

                                if (value != null){
                                        val mhsList = value.documents.mapNotNull {
                                                it.toObject(Mahasiswa::class.java)!!
                                        }
                                        trySend(mhsList)// try send memberikan fungsi untuk mengim data ke flow
                                }
                        }
                awaitClose{
                        mhsDocument.remove()
                }
        }

        override suspend fun deleteMhs(mahasiswa: Mahasiswa) {
                try {
                    firestore.collection("Mahasiswa")
                            .document(mahasiswa.nim)
                            .delete()
                            .await()
                }catch (e: Exception){
                        throw Exception("Gagal menghapus data mahasiswa: " +
                                "${e.message}")
                }
        }
}

package com.example.pertemuan13.ui.home.viewmodel

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.ui.PenyediaViewModel
import com.example.pertemuan13.ui.viewmodel.HomeUiState
import com.example.pertemuan13.ui.viewmodel.HomeViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { "Home" })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kontak")
            }
        }
    ){innerPadding ->
        HomeStatus(
            homeUiState = viewModel.mhsUiState,
            retryAction = {viewModel.getMhs()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = {
                viewModel.getMhs()
            }
        )
    }
}
@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf<Mahasiswa?>(null) }
    when (homeUiState){
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->{
            MhsLayout(mahasiswa = homeUiState.data,modifier = modifier.fillMaxWidth(), onDetailClick = {
                onDeleteClick(it)
            },
                onDeleteClick = {
                    onDeleteClick (it)
                }
            )
            deleteConfirmationRequired?.let { data ->
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        onDeleteClick(data)
                        deleteConfirmationRequired = null
                    },
                    onDeleteCancel = {
                        deleteConfirmationRequired = null
                    })
            }

        }
        is HomeUiState.Error -> OnError(retryAction,
            modifier = modifier.fillMaxSize(), message = homeUiState.e.message?:"Error")
    }
}
@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Text("Loading.........")
}

@Composable
fun OnError(retryAction: () -> Unit,
            modifier: Modifier,
            message: String){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Terjadi Kesalahan : $message",
            modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text( "Retry")
        }
    }
}
@Composable
fun MhsLayout(
    mahasiswa: List<Mahasiswa>,
    modifier: Modifier= Modifier,
    onDetailClick: (Mahasiswa) -> Unit,
    onDeleteClick: (Mahasiswa) -> Unit = {}
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(
            items = mahasiswa,
            itemContent = { mhs ->
                MhsCard(
                    mahasiswa = mhs,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDetailClick(mhs) },
                    onDeleteClick = {
                        onDeleteClick(mhs)
                    }
                )
            }
        )

    }
}
@Composable
fun MhsCard(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {}
){
    Card (
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    mahasiswa.nama,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(mahasiswa) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = mahasiswa.nim,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = mahasiswa.kelas,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = mahasiswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        dismissButton = {
            TextButton(onClick = { onDeleteCancel() }) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = { onDeleteConfirm() }) {
                Text(text = "Yes")
            }
        }
    )
}























































































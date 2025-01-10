package com.example.pertemuan13.ui.home.pages

import android.R.attr.onClick
import android.R.attr.padding
import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan13.ui.PenyediaViewModel
import com.example.pertemuan13.ui.viewmodel.FormState
import com.example.pertemuan13.ui.viewmodel.InsertViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory =
    PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        when (uiState){
            is FormState.Success -> {
                println(
                    "InsertMhsView: uistate is FormState.Success,navigate to home" +
                            uiState.message
                )

                coroutineScope.launch{
                    snackbarHostState.showSnackbar(uiState.message)
                }
                delay(700)
                onNavigate()
                viewModel.resetSnackBarMessage()
            }
            is FormState.Error -> {
                coroutineScope.launch{
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }
            else -> Unit
        }
    }
    Scaffold (
        modifier = Modifier,
        snackbarHost = { SnackbarHostState(hostState = snackbarHostState)},
        topBar = {
            TopAppBar(
                title = { Text("Tambah Mahasiswa") },
                navigationIcon = {
                    Button(onClick = onBack){
                        Text("Back")
                    }
                }
            )
        }
    ) {padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            InsertBodyMhs(
                uiState = uiEvent,
                homeUIState = uiState,
                onValueChange = {updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    if (viewModel.validateFields()){
                        viewModel.insertMhs()
                    }
                }
            )
        }
    }
}
package com.example.pertemuan13.ui.viewmodel



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.repository.RepositoryMhs
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.jvm.Throws

class HomeViewModel(
    private val repositoryMhs: RepositoryMhs
): ViewModel() {
    var mhsUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs(){
        viewModelScope.launch {
            repositoryMhs.getAllMhs()
                .onStart {
                    mhsUiState = HomeUiState.Loading
                }
                .catch {
                    mhsUiState = HomeUiState.Error(e = it)
                }
                .collect {
                    mhsUiState = if (it.isEmpty()) {
                        HomeUiState.Error(Exception("Belum ada data mahasiswa"))
                    } else {
                        HomeUiState.Success(it)

                    }
                }
        }
    }
}
sealed class HomeUiState{
    object Loading : HomeUiState()

    data class Success(val data: List<Mahasiswa>) : HomeUiState()

    data class Error(val e: Throwable) : HomeUiState()
}
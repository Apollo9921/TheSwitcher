package com.example.theswitcher_nunosilva.ui.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theswitcher_nunosilva.model.Division
import com.example.theswitcher_nunosilva.model.DivisionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val divisionRepository: DivisionRepository
): ViewModel() {

    private val _divisions = MutableStateFlow<DivisionsState>(DivisionsState.Success(emptyList()))
    private val divisions: StateFlow<DivisionsState> = _divisions

    var divisionsData = mutableStateListOf<Division>()
    var messageError = mutableStateOf("")
    var isSuccess = mutableStateOf(false)
    var isError = mutableStateOf(false)

    sealed class DivisionsState {
        data class Success(val divisions: List<Division>): DivisionsState()
        data class Error(val error: String): DivisionsState()
    }

    init {
        getDivisions()
    }

    private fun getDivisions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = divisionRepository.readAllData()
                _divisions.value = DivisionsState.Success(data)
            } catch (e: Exception) {
                _divisions.value = DivisionsState.Error(e.message ?: "Unknown error")
            }
            getDivisionsResponse()
        }
    }

    private fun getDivisionsResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            divisions.collect {
                when(it) {
                    is DivisionsState.Error -> {
                        messageError.value = it.error
                        isError.value = true
                        isSuccess.value = false
                    }
                    is DivisionsState.Success -> {
                        if (divisionsData.isNotEmpty()) {
                            divisionsData.clear()
                        }
                        divisionsData.addAll(it.divisions)
                        isError.value = false
                        messageError.value = ""
                        isSuccess.value = true
                    }
                }
            }
        }
    }

    fun updateDivisionMode(id: Int, mode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val division = divisionsData.first { it.id == id }
                division.switch = mode
                divisionRepository.updateDivisionMode(division)
                divisionsData[divisionsData.indexOf(division)] = division
            } catch (e: Exception) {
                _divisions.value = DivisionsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addDivision(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val division = Division(0, name, false)
                divisionRepository.addDivision(division)
                getDivisions()
            } catch (e: Exception) {
                _divisions.value = DivisionsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteDivision(division: Division) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                divisionRepository.deleteDivision(division)
                getDivisions()
            } catch (e: Exception) {
                _divisions.value = DivisionsState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
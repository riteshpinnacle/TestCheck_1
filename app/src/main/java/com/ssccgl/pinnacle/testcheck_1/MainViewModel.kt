package com.ssccgl.pinnacle.testcheck_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _data = MutableLiveData<List<ApiResponse>>()
    val data: LiveData<List<ApiResponse>> = _data

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.fetchData(
                    mapOf(
                        "paper_code" to "3228",
                        "email_id" to "anshulji100@gmail.com",
                        "exam_mode_id" to "1",
                        "test_series_id" to "2"
                    )
                )
                _data.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }
}

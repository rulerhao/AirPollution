package com.rulhouse.airpollution

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rulhouse.airpollution.model.remote.air_pollution.dto.Record
import com.rulhouse.airpollution.model.remote.air_pollution.use_cases.AirPollutionApiUseCases
import com.rulhouse.airpollution.model.remote.response.BaseResult
import com.rulhouse.airpollution.model.remote.response.RetrofitException
import com.rulhouse.airpollution.view.SearchStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val airPollutionApiUseCases: AirPollutionApiUseCases
) : ViewModel() {

    private val tag = this::class.simpleName

    private val _records = MutableLiveData<List<Record>>()
    val records: LiveData<List<Record>> = _records

    private val _searchText = MutableLiveData<String>("Apple")
    val searchText: LiveData<String> = _searchText

    private val _searchStatus = MutableLiveData(SearchStatus.NonSearch)
    val searchStatus: LiveData<SearchStatus> = _searchStatus

    private val _originalSearchBarVisibility = MutableLiveData(View.VISIBLE)
    val originalSearchBarVisibility: LiveData<Int> = _originalSearchBarVisibility

    private val _resultSearchBarVisibility = MutableLiveData(View.GONE)
    val resultSearchBarVisibility: LiveData<Int> = _resultSearchBarVisibility

    private var getRecordssJob: Job? = null

    init {
        Log.d(tag, "init")
        viewModelScope.launch {
            Log.d(tag, "launch")
            getCourses()
        }
    }

    private suspend fun getCourses() {
        getRecordssJob?.cancel()
        getRecordssJob = airPollutionApiUseCases.getRecords()
            .onStart {
                Log.d(tag, "onStart")
            }
            .catch { exception ->
                Log.d(tag, "catch")
                exception.printStackTrace()
            }
            .onEach { baseResult ->
                when (baseResult) {
                    is BaseResult.Success -> {
                        Log.d(tag, "Success!")
                        _records.value = baseResult.data
                        _searchText.postValue("Nice")
                    }
                    is BaseResult.Error -> {
                        val code = baseResult.rawResponse
                        if (code == RetrofitException.NoInternetException.ordinal) {
                            Log.d(tag, "No Internet Connection!")
                        } else if (code == RetrofitException.UnknownException.ordinal) {
                            Log.d(tag, "UnknownException!")
                        } else {

                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun openSearchBar() {
        _searchStatus.value = SearchStatus.NonInput
        _originalSearchBarVisibility.value = View.GONE
        _resultSearchBarVisibility.value = View.VISIBLE
    }

    fun closeSearchBar() {
        _searchStatus.value = SearchStatus.NonSearch
        _originalSearchBarVisibility.value = View.VISIBLE
        _resultSearchBarVisibility.value = View.GONE
    }

    fun onSearchTextChanged(text: CharSequence) {
        val searchText = text.toString()
        if (searchText.isNotBlank()) {
            _searchStatus.value = SearchStatus.Input
        } else {
            _searchStatus.value = SearchStatus.NonInput
        }
    }
}
package com.rulhouse.airpollution

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rulhouse.airpollution.model.remote.air_pollution.dto.Record
import com.rulhouse.airpollution.model.remote.air_pollution.use_cases.AirPollutionApiUseCases
import com.rulhouse.airpollution.model.remote.response.BaseResult
import com.rulhouse.airpollution.model.remote.response.RetrofitException
import com.rulhouse.airpollution.view.MiddleCardListener
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
) : ViewModel(), MiddleCardListener {

    private val tag = this::class.simpleName

    private val _records = MutableLiveData<List<Record>>()
    val records: LiveData<List<Record>> = _records

    private val _topCardRecords = MutableLiveData<List<Record>>()
    val topCardRecords: LiveData<List<Record>> = _topCardRecords

    private val _middleCardRecords = MutableLiveData<List<Record>>()
    val middleCardRecords: LiveData<List<Record>> = _middleCardRecords

    private val _searchText = MutableLiveData<String>("Apple")
    val searchText: LiveData<String> = _searchText

    private val _searchStatus = MutableLiveData(SearchStatus.NonSearch)
    val searchStatus: LiveData<SearchStatus> = _searchStatus

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
                        getCardRecordsByPM2p5(records.value)
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
    }

    fun closeSearchBar() {
        _searchStatus.value = SearchStatus.NonSearch
        getCardRecordsByPM2p5(records.value)
    }

    fun showDetail() {
        Log.d(tag, "TestShowDetail")
    }

    fun onSearchTextChanged(text: CharSequence) {
        _searchText.value = text.toString()
        if (searchText.value != null) {
            if (searchText.value?.isNotBlank() == true) {
                _searchStatus.value = SearchStatus.Input
            } else {
                _searchStatus.value = SearchStatus.NonInput
            }
            searchRecords(searchText.value!!)
        }
    }

    private fun getCardRecordsByPM2p5(records: List<Record>?) {
        if (records != null) {
            _topCardRecords.value = records.filter {
                it.pm2_5.toInt() <= 30
            }
            _middleCardRecords.value = records.filter {
                it.pm2_5.toInt() > 30
            }
        }
    }

    private fun searchRecords(searchText: String) {
        val newRecords = mutableListOf<Record>()
        records.value?.forEach {
            if (it.sitename.contains(searchText)) newRecords.add(it)
        }
        _middleCardRecords.value = newRecords
    }

    override fun onDetailClicked(record: Record) {
        showDetail()
    }
}
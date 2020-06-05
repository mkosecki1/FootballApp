package com.footballapp.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.footballapp.model.StandingsModel
import com.footballapp.net.ResponseCall
import com.footballapp.repository.Repository
import kotlinx.coroutines.launch

class StandingsViewModel(private val repository: Repository) : ViewModel() {

    private val _standingsStatus = MutableLiveData<ResponseCall<StandingsModel>>()
    val standingsStatus: LiveData<ResponseCall<StandingsModel>>
        get() = _standingsStatus

    fun loadStandings(leagueId: String) {
        viewModelScope.launch {
            val result = repository.getAllStandings(leagueId)
            _standingsStatus.value = result
        }
    }
}

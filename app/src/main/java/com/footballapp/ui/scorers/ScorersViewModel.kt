package com.footballapp.ui.scorers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.footballapp.model.ScorersModel
import com.footballapp.net.ResponseCall
import com.footballapp.repository.Repository
import kotlinx.coroutines.launch

class ScorersViewModel(private val repository: Repository) : ViewModel() {

    private val _scorersStatus = MutableLiveData<ResponseCall<ScorersModel>>()
    val scorersStatus: LiveData<ResponseCall<ScorersModel>>
        get() = _scorersStatus

    fun loadScorers() {
        viewModelScope.launch {
            val result = repository.getAllScorers()
            _scorersStatus.value = result
        }
    }
}
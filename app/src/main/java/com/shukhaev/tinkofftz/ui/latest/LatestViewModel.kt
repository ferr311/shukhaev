package com.shukhaev.tinkofftz.ui.latest

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shukhaev.tinkofftz.model.Post
import com.shukhaev.tinkofftz.repo.Repository
import com.shukhaev.tinkofftz.repo.Resource
import kotlinx.coroutines.launch

class LatestViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val postsListLiveData: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>> = postsListLiveData

    private val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = errorLiveData

    init {
        getLatestGifs()
    }

    private fun getLatestGifs() = viewModelScope.launch {
        when (val res = repository.getLatestGif()) {
            is Resource.Error -> errorLiveData.value = res.message
            is Resource.Success -> postsListLiveData.postValue(res.data)
        }
    }
}
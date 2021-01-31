package com.shukhaev.tinkofftz.ui.latest

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shukhaev.tinkofftz.model.Post
import com.shukhaev.tinkofftz.network.DevLifeApi
import kotlinx.coroutines.launch

class LatestViewModel @ViewModelInject constructor(private val api: DevLifeApi) : ViewModel() {

    private val postsListLiveData: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>>
        get() = postsListLiveData

    init {
        getLatestGifs()
    }

    private fun getLatestGifs() = viewModelScope.launch {
        val res = api.getLatestGif().result
        postsListLiveData.postValue(res)

    }
}
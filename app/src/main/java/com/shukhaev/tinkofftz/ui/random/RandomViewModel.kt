package com.shukhaev.tinkofftz.ui.random

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.shukhaev.tinkofftz.model.Post
import com.shukhaev.tinkofftz.network.LoadEvent
import com.shukhaev.tinkofftz.repo.Repository
import com.shukhaev.tinkofftz.repo.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RandomViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val postMutableLiveData = MutableLiveData<Post?>()
    val post: LiveData<Post?> = postMutableLiveData
    private val postChannel = Channel<LoadEvent>()
    val postEvent = postChannel.receiveAsFlow()

    private var clickCount = 0
    private val listPosts: MutableList<Post> = mutableListOf()

    init {
        getRandomGif()
    }

    private fun getRandomGif() = viewModelScope.launch {
        postChannel.send(LoadEvent.Loading)
        when (val res = repository.getRandomGif()) {
            is Resource.Error -> {
                postChannel.send(LoadEvent.Error(res.message ?: ""))
                clickCount -= 1
                if (clickCount <= 0) clickCount = 0
                postChannel.send(LoadEvent.FirstElement)
            }
            is Resource.Success -> {
                res.data?.let { listPosts.add(it) }
                postMutableLiveData.postValue(res.data)
            }
        }
    }

    fun btnNextClicked() {
        clickCount += 1
        if (clickCount < listPosts.size) {
            postMutableLiveData.postValue(listPosts[clickCount])
        } else {
            getRandomGif()
        }
    }

    fun btnBackClicked() = viewModelScope.launch {
        clickCount -= 1
        if (clickCount == 0) {
            postChannel.send(LoadEvent.FirstElement)
            postMutableLiveData.postValue(listPosts[clickCount])
        } else {
            postMutableLiveData.postValue(listPosts[clickCount])
        }
    }
}
package com.shukhaev.tinkofftz.ui.random

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.shukhaev.tinkofftz.model.Post
import com.shukhaev.tinkofftz.network.DevLifeApi
import com.shukhaev.tinkofftz.network.LoadEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RandomViewModel @ViewModelInject constructor(
    private val api: DevLifeApi,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var clickCount = 0

    private val listPosts: MutableList<Post> = mutableListOf()
    private val postMutableLiveData = MutableLiveData<Post?>()
    val post: LiveData<Post?>
        get() = postMutableLiveData

    private val postChannel = Channel<LoadEvent>()
    val postEvent = postChannel.receiveAsFlow()


    init {
        getRandomGif()

    }

    private fun getRandomGif() = viewModelScope.launch {

        postChannel.send(LoadEvent.Loading)
        val resp = api.getRandomGif()
        if (resp.isSuccessful && resp.body() != null) {
            val post = resp.body()
            post?.let { listPosts.add(it) }
            postMutableLiveData.postValue(post)
        } else {
            postChannel.send(LoadEvent.Error("Some loading error"))
            clickCount -= 1
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
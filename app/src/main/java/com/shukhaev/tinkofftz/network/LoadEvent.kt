package com.shukhaev.tinkofftz.network

sealed class LoadEvent {
    object Loading : LoadEvent()
    data class Error(val msg: String) : LoadEvent()
    object FirstElement : LoadEvent()
}
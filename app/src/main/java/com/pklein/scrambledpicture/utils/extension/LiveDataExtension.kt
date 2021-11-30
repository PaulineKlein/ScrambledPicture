package com.pklein.scrambledpicture.utils

import androidx.lifecycle.*


fun <T> LiveData<T>.bind(owner: LifecycleOwner, onObserved: (T) -> Unit) {
    this.observe(owner, Observer {
        onObserved.invoke(it)
    })
}

fun <S, T> LiveData<S>.map(mapFunction: (S) -> T): LiveData<T> =
    Transformations.map(this, mapFunction)

// usefull when we need to recompose the view when the elements change
// (here the search gives always the same result, therefore we can't use it)
fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    var latestValue: T? = null
    mutableLiveData.addSource(this) {
        if (latestValue != it) {
            mutableLiveData.value = it
            latestValue = it
        }
    }
    return mutableLiveData
}
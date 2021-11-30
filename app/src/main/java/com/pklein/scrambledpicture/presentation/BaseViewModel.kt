package com.pklein.scrambledpicture.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pklein.scrambledpicture.utils.Action
import com.pklein.scrambledpicture.utils.State
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : State, A : Action>(
    private val initialState: S
) : ViewModel() {

    private val _state = MutableLiveData<S>().apply {
        value = initialState
    }

    val state: LiveData<S>
        get() = _state

    protected fun updateState(handler: (S) -> S) {
        val currentState = _state.value ?: initialState
        _state.value = handler(currentState)
    }

    private val intents = Channel<A>()

    fun send(intent: A) = viewModelScope.launch { intents.send(intent) }

    init {
        viewModelScope.launch {
            intents.consumeEach { intent ->
                handle(intent)
            }
        }
    }

    abstract fun handle(action: A)
}
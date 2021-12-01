package com.pklein.scrambledpicture.presentation.home

import androidx.lifecycle.viewModelScope
import com.pklein.scrambledpicture.data.model.GameData
import com.pklein.scrambledpicture.presentation.BaseViewModel
import com.pklein.scrambledpicture.utils.Action
import com.pklein.scrambledpicture.utils.State
import kotlinx.coroutines.launch

data class HomeState(
    var dataToShow: GameData? = null,
    var isCorrect: Boolean? = null,
    var isFinish: Boolean = false
) : State

sealed class HomeAction : Action {
    object ResetGame : HomeAction()
    object GetAllGame : HomeAction()
    object GetNextGame : HomeAction()
    data class CheckAnswer(val answer: String) : HomeAction()
}

class HomeViewModel(
    private val homeInteractor: HomeInteractor = HomeInteractor(),
    initialState: HomeState = HomeState()
) : BaseViewModel<HomeState, HomeAction>(initialState) {

    override fun handle(action: HomeAction) {
        when (action) {
            is HomeAction.ResetGame -> resetGame()
            is HomeAction.GetAllGame -> getAllGame()
            is HomeAction.GetNextGame -> getNextGame()
            is HomeAction.CheckAnswer -> checkAnswer(action.answer)
        }
    }

    private fun resetGame() {
        homeInteractor.resetGame() // set step to 0
        getNextGame() // display first step
    }

    private fun getAllGame() {
        viewModelScope.launch {
            homeInteractor.getAllGame() // initialise game
            getNextGame() // display first step
        }
    }

    private fun checkAnswer(answer: String) {
        updateState {
            it.copy(
                isCorrect = homeInteractor.checkAnswer(answer)
            )
        }
    }

    private fun getNextGame() {
        if (homeInteractor.canGameContinue()) {
            updateState {
                it.copy(
                    dataToShow = homeInteractor.getNextGame(),
                    isFinish = false,
                    isCorrect = null
                )
            }
        } else { // end of game
            updateState {
                it.copy(
                    dataToShow = null,
                    isFinish = true,
                    isCorrect = null
                )
            }
        }
    }
}
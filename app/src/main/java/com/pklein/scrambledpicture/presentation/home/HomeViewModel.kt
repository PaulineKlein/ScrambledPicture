package com.pklein.scrambledpicture.presentation.home

import androidx.lifecycle.viewModelScope
import com.pklein.scrambledpicture.data.model.GameData
import com.pklein.scrambledpicture.presentation.BaseViewModel
import com.pklein.scrambledpicture.utils.Action
import com.pklein.scrambledpicture.utils.State
import com.pklein.scrambledpicture.utils.extension.formatAnswer
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

    var gameDataList: List<GameData>? = listOf()
    var gameState: Int = 0

    override fun handle(action: HomeAction) {
        when (action) {
            is HomeAction.ResetGame -> resetGame()
            is HomeAction.GetAllGame -> getAllGame()
            is HomeAction.GetNextGame -> getNextGame()
            is HomeAction.CheckAnswer -> checkAnswer(action.answer)
        }
    }

    private fun resetGame() {
        gameState = 0
        getNextGame()
    }

    private fun getAllGame() {
        viewModelScope.launch {
            gameDataList = homeInteractor.getAllGame()
            getNextGame()
        }
    }

    private fun checkAnswer(answer: String) {
        val isCorrect =
            (answer.formatAnswer() == gameDataList?.get(gameState)?.answer?.formatAnswer())
        if (isCorrect) {
            gameState += 1
        }
        updateState {
            it.copy(
                isCorrect = isCorrect
            )
        }
    }

    private fun getNextGame() {
        if (gameState < gameDataList?.size ?: 0) {
            updateState {
                it.copy(
                    dataToShow = gameDataList?.get(gameState),
                    isFinish = false,
                    isCorrect = null
                )
            }
        } else {
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
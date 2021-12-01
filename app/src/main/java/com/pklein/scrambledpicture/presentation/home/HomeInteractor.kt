package com.pklein.scrambledpicture.presentation.home

import com.pklein.scrambledpicture.data.model.GameData
import com.pklein.scrambledpicture.data.repository.GameDataRepository
import com.pklein.scrambledpicture.utils.extension.formatAnswer

class HomeInteractor(private val repository: GameDataRepository = GameDataRepository()) {

    var gameDataList: List<GameData>? = listOf()
    var gameState: Int = 0

    fun checkAnswer(answer: String): Boolean {
        val isCorrect =
            (answer.formatAnswer() == gameDataList?.get(gameState)?.answer?.formatAnswer())
        if (isCorrect) {
            gameState += 1
        }
        return isCorrect
    }

    fun resetGame() {
        gameState = 0
    }

    fun canGameContinue(): Boolean {
        return gameState < gameDataList?.size ?: 0
    }

    suspend fun getAllGame() {
        gameDataList = repository.getAllGame()
    }

    fun getNextGame(): GameData? {
        return gameDataList?.get(gameState)
    }
}
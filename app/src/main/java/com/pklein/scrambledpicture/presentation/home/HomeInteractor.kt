package com.pklein.scrambledpicture.presentation.home

import com.pklein.scrambledpicture.data.model.GameData
import com.pklein.scrambledpicture.data.repository.GameDataRepository

class HomeInteractor(private val repository: GameDataRepository = GameDataRepository()) {

    suspend fun resetGame() {

    }

    suspend fun getAllGame(): List<GameData> {
        return repository.getAllGame()
    }

    suspend fun getNextGame() {

    }
}
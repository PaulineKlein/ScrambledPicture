package com.pklein.scrambledpicture.data.repository

import com.pklein.scrambledpicture.data.model.GameData

interface IGameDataRepository {
    suspend fun getGameStep(step: Int): GameData
    suspend fun getAllGame(): List<GameData>
    suspend fun insertAllData(data: List<GameData>)
}
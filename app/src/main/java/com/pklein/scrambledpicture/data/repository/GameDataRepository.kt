package com.pklein.scrambledpicture.data.repository

import com.pklein.scrambledpicture.ScrambledPictureApplication
import com.pklein.scrambledpicture.data.dao.GameDataDao
import com.pklein.scrambledpicture.data.model.GameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameDataRepository(
    private val gameDataDao: GameDataDao =
        ScrambledPictureApplication.database.gameDataDao()
) : IGameDataRepository {

    override suspend fun getAllGame(): List<GameData> = withContext(Dispatchers.IO) {
        gameDataDao.getAllGame()
    }

    override suspend fun getGameStep(step: Int): GameData = withContext(Dispatchers.IO) {
        gameDataDao.getGameStep(step)
    }

    override suspend fun insertAllData(data: List<GameData>) = withContext(Dispatchers.IO) {
        gameDataDao.insertAllData(data)
    }
}
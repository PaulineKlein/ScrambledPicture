package com.pklein.scrambledpicture.data.repository

import com.pklein.scrambledpicture.ScrambledPictureApplication
import com.pklein.scrambledpicture.data.dao.PlayerDao
import com.pklein.scrambledpicture.data.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerRepository(
    private val playerDao: PlayerDao =
        ScrambledPictureApplication.database.playerDao()
) : IPlayerRepository {

    override suspend fun getPlayerFromName(name: String): Player = withContext(Dispatchers.IO) {
        playerDao.getPlayerFromName(name)
    }

    override suspend fun insertNewPlayer(player: Player) = withContext(Dispatchers.IO) {
        playerDao.insertNewPlayer(player)
    }
}
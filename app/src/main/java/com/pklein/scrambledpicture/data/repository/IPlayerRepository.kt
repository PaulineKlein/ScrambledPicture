package com.pklein.scrambledpicture.data.repository

import com.pklein.scrambledpicture.data.model.Player

interface IPlayerRepository {
    suspend fun getPlayerFromName(name: String): Player
    suspend fun insertNewPlayer(player: Player)
}
package com.pklein.scrambledpicture.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pklein.scrambledpicture.data.model.Player

@Dao
abstract class PlayerDao {
    @Query("SELECT * FROM player where playerName = :name")
    abstract fun getPlayerFromName(name: String): Player

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNewPlayer(player: Player)
}
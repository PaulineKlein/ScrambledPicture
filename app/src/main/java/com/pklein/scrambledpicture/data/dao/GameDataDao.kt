package com.pklein.scrambledpicture.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pklein.scrambledpicture.data.model.GameData

@Dao
abstract class GameDataDao {
    @Query("SELECT * FROM game_data")
    abstract fun getAllGame(): List<GameData>

    @Query("SELECT * FROM game_data where id = :step")
    abstract fun getGameStep(step: Int): GameData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllData(data: List<GameData>)
}
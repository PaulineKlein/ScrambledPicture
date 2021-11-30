package com.pklein.scrambledpicture.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pklein.scrambledpicture.data.dao.GameDataDao
import com.pklein.scrambledpicture.data.dao.PlayerDao
import com.pklein.scrambledpicture.data.model.GameData
import com.pklein.scrambledpicture.data.model.Player

@Database(
    entities = [
        GameData::class,
        Player::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DbConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDataDao(): GameDataDao
    abstract fun playerDao(): PlayerDao
}
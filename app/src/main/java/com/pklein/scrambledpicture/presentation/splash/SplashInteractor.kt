package com.pklein.scrambledpicture.presentation.splash

import android.content.res.AssetManager
import com.pklein.scrambledpicture.data.DbConverter
import com.pklein.scrambledpicture.data.model.GameData
import com.pklein.scrambledpicture.data.repository.GameDataRepository

class SplashInteractor(private val gameDataRepository: GameDataRepository = GameDataRepository()) {
    fun loadJSONFromAsset(asset: AssetManager): List<GameData>? {
        val converter = DbConverter()
        converter.loadJSONFromAsset(asset)?.let {
            return converter.jsonToGameData(it)
        }
        return null
    }

    suspend fun insertGameData(data: List<GameData>) {
        gameDataRepository.insertAllData(data)
    }
}
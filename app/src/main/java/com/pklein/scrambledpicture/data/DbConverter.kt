package com.pklein.scrambledpicture.data

import android.content.res.AssetManager
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pklein.scrambledpicture.data.model.GameData
import com.pklein.scrambledpicture.data.model.GameDataType
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

const val JSON_FILE = "data.json"

class DbConverter {
    val gson by lazy {
        Gson()
    }

    @TypeConverter
    fun gameDataTypeToString(type: GameDataType): String {
        return type.imageName
    }

    @TypeConverter
    fun stringToGameDataType(value: String): GameDataType? {
        return GameDataType.values()
            .find { gameDataType -> gameDataType.imageName == value }
    }

    @TypeConverter
    fun jsonToGameData(info: String): List<GameData> {
        val type = object : TypeToken<List<GameData>>() {}.type
        return gson.fromJson(info, type)
    }

    @TypeConverter
    fun gameDataToJson(info: GameData): String {
        val type = object : TypeToken<GameData>() {}.type
        return gson.toJson(info, type)
    }

    fun loadJSONFromAsset(asset: AssetManager): String? {
        var json: String? = null
        json = try {
            val input: InputStream = asset.open(JSON_FILE)
            val size: Int = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}
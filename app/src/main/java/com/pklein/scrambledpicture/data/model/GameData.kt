package com.pklein.scrambledpicture.data.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

enum class GameDataType(val imageName: String) {
    CAT("cat"),
    DROID("droid"),
    COFFEE("coffee")
}

@Parcelize
@Entity(tableName = "game_data", primaryKeys = ["id"])
data class GameData(
    @Expose
    @SerializedName("i")
    var id: Int,
    @Expose
    @SerializedName("in")
    var imageName: String,
    @Expose
    @SerializedName("a")
    var answer: String
) : Parcelable
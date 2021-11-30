package com.pklein.scrambledpicture.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "player", primaryKeys = ["id"],
    indices = [Index(value = ["playerName"], unique = true)]
)
data class Player(
    var id: Int,
    var playerName: String,
    var score: Int,
    var step: Int = 0 // step image where player stopped to play
) : Parcelable
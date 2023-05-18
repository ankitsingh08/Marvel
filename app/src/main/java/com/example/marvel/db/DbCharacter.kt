package com.example.marvel.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvel.comicsToString
import com.example.marvel.db.Constants.CHARACTER_TABLE
import com.example.marvel.model.CharacterResult

@Entity(tableName = CHARACTER_TABLE)
data class DbCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val apiId: Int?,
    val name: String?,
    val thumbNail: String?,
    val comics: String?,
    val description: String?
) {
    companion object {
        fun fromCharacter(characterResult: CharacterResult) =
            DbCharacter(
                id = 0,
                apiId = characterResult.id,
                name = characterResult.name,
                thumbNail = characterResult?.thumbnail?.path + "." + characterResult?.thumbnail?.extension,
                comics = characterResult?.comics?.items?.mapNotNull { it.name }?.comicsToString() ?: "no comics",
                description = characterResult.description
            )
    }
}

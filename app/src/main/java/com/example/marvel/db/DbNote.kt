package com.example.marvel.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvel.model.Note

@Entity(tableName = Constants.NOTES_TABLE)
data class DbNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val characterId: Int,
    val title: String,
    val text: String
) {
    companion object {
        fun fromNote(note: Note) =
            DbNote(
                id = 0,
                characterId = note.characterId,
                title = note.title,
                text = note.text
            )
    }
}

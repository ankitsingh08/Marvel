package com.example.marvel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.db.CollectionDbRepo
import com.example.marvel.model.CharacterResult
import com.example.marvel.db.DbCharacter
import com.example.marvel.db.DbNote
import com.example.marvel.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDbViewModel @Inject constructor(private val repo: CollectionDbRepo): ViewModel(){

    private val currentCharacter = MutableStateFlow<DbCharacter?>(null)
    val collection = MutableStateFlow<List<DbCharacter>>(listOf())
    val notes = MutableStateFlow<List<DbNote>>(listOf())

    init {
        getCollection()
        getNotes()
    }

    private fun getCollection() {
        viewModelScope.launch {
            repo.getCharactersFromRepo().collect {
                collection.value = it
            }
        }
    }

    private fun getNotes() {
        viewModelScope.launch {
            repo.getAllNotes().collect {
                notes.value = it
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addNoteToRepo(DbNote.fromNote(note))
        }
    }

    fun deleteNote(dbNote: DbNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNoteFromRepo(dbNote)
        }
    }

    fun setCurrentCharacterId(characterId: Int?) {
        characterId?.let {
            viewModelScope.launch {
                repo.getCharacterFromRepo(it).collect {
                    currentCharacter.value = it
                }
            }
        }
    }

    fun addCharacter(character: CharacterResult) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addCharacterToRepo(DbCharacter.fromCharacter(character))
        }
    }

    fun deleteCharacter(character: DbCharacter) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAllNotes(character)
            repo.deleteCharacterFromRepo(character)
        }
    }
}
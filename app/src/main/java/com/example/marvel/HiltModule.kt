package com.example.marvel

import android.content.Context
import androidx.room.Room
import com.example.marvel.api.ApiService
import com.example.marvel.api.MarvelAPiRepo
import com.example.marvel.db.CharacterDao
import com.example.marvel.db.CollectionDb
import com.example.marvel.db.CollectionDbRepo
import com.example.marvel.db.CollectionDbRepoImpl
import com.example.marvel.db.Constants.DB
import com.example.marvel.db.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun providesApiRepo() = MarvelAPiRepo(ApiService.api)

    @Provides
    fun provideCollectionDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDb::class.java, DB).build()

    @Provides
    fun provideCharacterDao(collectionDb: CollectionDb) = collectionDb.characterDao()

    @Provides
    fun provideNoteDao(collectionDb: CollectionDb) = collectionDb.noteDao()

    @Provides
    fun provideDbRepoImpl(characterDao: CharacterDao, noteDao: NoteDao): CollectionDbRepo = CollectionDbRepoImpl(characterDao, noteDao)

}
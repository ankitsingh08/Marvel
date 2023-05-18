package com.example.marvel

sealed class Destination(val route: String) {
    object Library: Destination("library")
    object Collection: Destination("collection")
    object Character: Destination("character/{characterId}") {
        fun createRoute(characterId: Int?) = "character/$characterId"
    }
}
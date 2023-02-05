package com.netimur.tinkofffintech2023.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film_card_representations")
data class FilmCardRepresentation(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val genre: String,
    val year: String,
    val posterUrlPreview: String,
    var isFavourite: Boolean
) {
}
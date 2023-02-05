package com.netimur.tinkofffintech2023.data.database.daos

import androidx.room.*
import com.netimur.tinkofffintech2023.data.model.FilmCardRepresentation

@Dao
interface FilmCardRepresentationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilmCardRepresentation(filmCardRepresentation: FilmCardRepresentation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilmCardRepresentations(filmCardRepresentations: List<FilmCardRepresentation>)

    @Query("SELECT * FROM film_card_representations")
    suspend fun getFilmCardRepresentations(): List<FilmCardRepresentation>

    @Delete
    suspend fun deleteFilmCardRepresentation(film: FilmCardRepresentation)

}
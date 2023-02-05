package com.netimur.tinkofffintech2023.ui.mainpage

import com.netimur.tinkofffintech2023.data.model.FilmCardRepresentation

interface RemoveCallback {
    fun onRemove(film: FilmCardRepresentation, position: Int)
}
package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object SongGenreMap: IntIdTable("song_genre_map") {
    val songId = reference("song_id", Songs)
    val genreId = reference("genre_id", Genres)
}
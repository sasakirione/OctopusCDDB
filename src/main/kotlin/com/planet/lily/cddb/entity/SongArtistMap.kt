package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object SongArtistMap: IntIdTable("song_artist_map") {
    val songId = reference("song_id", Songs)
    val artistId = reference("artist_id", Artists)
}
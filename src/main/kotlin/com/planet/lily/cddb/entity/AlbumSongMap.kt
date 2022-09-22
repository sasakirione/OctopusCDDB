package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object AlbumSongMap: IntIdTable("album_song_map") {
    val albumId = reference("album_id", Albums)
    val songId = reference("song_id", Songs)
    val trackNumber = integer("track_number")
    val discNumber = integer("disc_number")
}
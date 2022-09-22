package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object ArtistMap: IntIdTable("artist_map") {
    val artist = reference("artist", Artists)
    val nameId = reference("name_id", MusicianNameMap)
}
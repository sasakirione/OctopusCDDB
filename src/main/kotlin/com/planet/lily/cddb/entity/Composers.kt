package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Composers: IntIdTable("composers") {
    val songId = reference("song_id", Songs)
    val composerId = reference("composer_id", MusicianNameMap)
}
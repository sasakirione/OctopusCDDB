package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object SongTypes: IntIdTable("song_types") {
    val name = varchar("name", 63)
}
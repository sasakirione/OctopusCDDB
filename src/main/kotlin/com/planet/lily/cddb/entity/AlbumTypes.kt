package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object AlbumTypes: IntIdTable("album_types") {
    val name = varchar("name", 63)
}
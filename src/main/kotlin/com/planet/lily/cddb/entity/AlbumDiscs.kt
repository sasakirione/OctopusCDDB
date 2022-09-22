package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object AlbumDiscs : IntIdTable("album_discs") {
    val albumId = reference("album_id", Albums)
    val discNumber = integer("disc_number")
    val discTitle = varchar("disc_title", 255).nullable()
    val cddb_id = text("cddb_id").nullable()
}
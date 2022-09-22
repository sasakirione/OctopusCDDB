package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object OriginalSongs: IntIdTable("original_songs") {
    val title = varchar("title", 127)
}
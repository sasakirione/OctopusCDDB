package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object LyricistTypes: IntIdTable("lyricist_types") {
    val name = varchar("name", 63)
}

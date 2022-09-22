package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object MusicianNameMap: IntIdTable("musician_name_map") {
    val name = varchar("name", 63)
    val musician = reference("musician", Musicians)
}
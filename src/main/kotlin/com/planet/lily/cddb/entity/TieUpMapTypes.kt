package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object TieUpMapTypes: IntIdTable("tie_up_map_types") {
    val name = varchar("name", 63)
}

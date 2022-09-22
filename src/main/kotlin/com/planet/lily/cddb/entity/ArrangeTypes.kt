package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object ArrangeTypes: IntIdTable("arrange_types") {
    val name = varchar("name", 63)
}

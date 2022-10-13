package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object CreatorTypes : IntIdTable("creator_types") {
    val typeName = varchar("type_name", 31)
}
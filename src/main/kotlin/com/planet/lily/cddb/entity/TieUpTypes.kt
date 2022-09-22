package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object TieUpTypes: IntIdTable("tie_up_types") {
    val name = varchar("name", 63)
}
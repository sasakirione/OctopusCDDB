package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Brands: IntIdTable() {
    val name = varchar("name", 127)
}

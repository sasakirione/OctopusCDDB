package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Publishers: IntIdTable() {
    val name = varchar("name", 63)
}

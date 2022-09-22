package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Musicians: IntIdTable() {
    val name = varchar("name", 63)
}
package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Countries: IntIdTable() {
    val name = varchar("name", 32)
    val code = varchar("code", 3)
}

package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Genres: IntIdTable() {
    val name = varchar("name", 63)
    val description = varchar("description", 255)
    val parent = reference("parent", Genres).nullable()
}
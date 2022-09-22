package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Labels: IntIdTable() {
    val name = varchar("name", 63)
    val publisher = reference("publisher", Publishers).nullable()
    val country = reference("country", Countries)
    val isMajor = bool("is_major").nullable()
}
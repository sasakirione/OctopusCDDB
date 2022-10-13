package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Creators: IntIdTable("creators") {
    val creatorName = varchar("creator_name", 63)
}
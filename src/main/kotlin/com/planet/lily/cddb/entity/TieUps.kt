package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object TieUps: IntIdTable("tie_ups") {
    val name = varchar("name", 127)
    val typeId = reference("type_id", TieUpTypes)
    val brands = reference("brand_id", Brands).nullable()
}
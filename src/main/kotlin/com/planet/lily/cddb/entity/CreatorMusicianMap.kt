package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object CreatorMusicianMap: IntIdTable("creator_musician_map") {
    val creator = reference("creator", Creators)
    val musician = reference("musician", Musicians)
}
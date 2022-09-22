package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Arrangers: IntIdTable() {
    val songId = reference("song_id", Songs)
    val arrangersId = reference("arrangers_id", MusicianNameMap)
    val arrangeType = reference("arrange_type", ArrangeTypes)
}
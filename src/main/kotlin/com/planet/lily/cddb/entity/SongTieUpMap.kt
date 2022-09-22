package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object SongTieUpMap: IntIdTable("song_tie_up_map") {
    val songId = reference("song_id", Songs)
    val tieUpId = reference("tie_up_id", TieUps)
    val tieUpMapType = reference("tie_up_map_type", TieUpMapTypes)
}
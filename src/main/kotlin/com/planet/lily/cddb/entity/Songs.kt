package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Songs: IntIdTable() {
    val originalId = reference("original_id", OriginalSongs)
    val title = varchar("title", 127)
    val songType = reference("song_type", SongTypes)
}
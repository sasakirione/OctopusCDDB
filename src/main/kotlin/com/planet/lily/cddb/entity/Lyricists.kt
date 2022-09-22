package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Lyricists: IntIdTable() {
    val songId = reference("song_id", Songs)
    val lyricistId = reference("lyricist_id", MusicianNameMap)
    val lyricistType = reference("lyricist_type", LyricistTypes)
}
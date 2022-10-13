package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object OriginalCreators : IntIdTable("original_creators") {
    val originalSong = reference("original_song", OriginalSongs)
    val createType = reference("create_type", CreatorTypes)
    val createArtist = reference("creator", Creators)
}
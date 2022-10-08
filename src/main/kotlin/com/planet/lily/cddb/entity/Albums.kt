package com.planet.lily.cddb.entity

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object Albums: IntIdTable() {
    val title = varchar("title", 127)
    val release = date("release")
    val albumLabel = reference("album_label", Labels).nullable()
    val albumType = reference("album_type", AlbumTypes).nullable()
    val recordNumber = varchar("record_number", 31).nullable()
    val albumVersion = varchar("album_version", 63).nullable()
}
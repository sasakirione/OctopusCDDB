package com.planet.lily.cddb.model

import com.planet.lily.cddb.entity.AlbumDiscs
import com.planet.lily.cddb.entity.AlbumSongMap
import com.planet.lily.cddb.entity.Albums
import com.planet.lily.cddb.plugins.AlbumJson
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class Album {
    fun getAlbumList() = transaction {
        Albums.selectAll().map { it[Albums.id].value to (it[Albums.title] + " " + it[Albums.albumVersion].orEmpty()) }
    }

    fun getAlbum(id: Int) = transaction {
        Albums.select { Albums.id eq id }.first()
    }

    fun addAlbum(album: AlbumJson) = transaction {
        Albums.insert {
            it[title] = album.title
            it[release] = LocalDate.parse(album.releaseDate)
            it[albumLabel] = album.label
            it[albumType] = album.albumType
            it[recordNumber] = album.recordNumber
            it[albumVersion] = album.albumVersion
        }
    }

    fun updateAlbum(id: Int, album: AlbumJson) = transaction {
        Albums.update({ Albums.id eq id }) {
            it[title] = album.title
            it[release] = LocalDate.parse(album.releaseDate)
            it[albumLabel] = album.label
            it[albumType] = album.albumType
            it[recordNumber] = album.recordNumber
            it[albumVersion] = album.albumVersion
        }
    }

    fun deleteAlbum(id: Int) = transaction {
        AlbumDiscs.deleteWhere { AlbumDiscs.albumId eq id }
        AlbumSongMap.deleteWhere { AlbumSongMap.albumId eq id }
        Albums.deleteWhere { Albums.id eq id }
    }
}
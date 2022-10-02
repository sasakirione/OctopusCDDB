package com.planet.lily.cddb.model

import com.planet.lily.cddb.entity.*
import com.planet.lily.cddb.plugins.AlbumDiscJson
import com.planet.lily.cddb.plugins.AlbumJson
import com.planet.lily.cddb.plugins.AlbumJson2
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class Album {
    fun getAlbumList() = transaction {
        Albums.selectAll().map { it[Albums.id].value to (it[Albums.title] + " " + it[Albums.albumVersion].orEmpty()) }
    }

    fun getAlbum(id: Int) = transaction {
        Albums.innerJoin(Labels).innerJoin(AlbumTypes).select { Albums.id eq id }.map {
            AlbumJson2(it[Albums.title], it[Albums.release].toString(), it[Labels.name],
                it[AlbumTypes.name], it[Albums.recordNumber], it[Albums.albumVersion], getAlbumDiscs(id))
        }.first()
    }

    private fun getAlbumDiscs(id: Int)  =
        AlbumDiscs.select { AlbumDiscs.albumId eq id }.map {
            AlbumDiscJson(it[AlbumDiscs.discNumber], it[AlbumDiscs.discTitle], it[AlbumDiscs.cddb_id])
        }

    fun addAlbum(album: AlbumJson) = transaction {
        val albumId = Albums.insert {
            it[title] = album.title
            it[release] = LocalDate.parse(album.releaseDate)
            it[albumLabel] = album.label
            it[albumType] = album.albumType
            it[recordNumber] = album.recordNumber
            it[albumVersion] = album.albumVersion
        } get Albums.id
        if (album.discs.isNotEmpty()) {
            album.discs.forEach { disc ->
                AlbumDiscs.insert {
                    it[AlbumDiscs.albumId] = albumId
                    it[discNumber] = disc.discNumber
                    it[discTitle] = disc.discTitle
                    it[cddb_id] = disc.cddbId
                }
            }
        }
        albumId.value
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
        if (album.discs.isNotEmpty()) {
            album.discs.forEach { disc ->
                AlbumDiscs.update({ AlbumDiscs.albumId eq id}) {
                    it[discNumber] = disc.discNumber
                    it[discTitle] = disc.discTitle
                    it[cddb_id] = disc.cddbId
                }
            }
        }
    }

    fun deleteAlbum(id: Int) = transaction {
        AlbumDiscs.deleteWhere { AlbumDiscs.albumId eq id }
        AlbumSongMap.deleteWhere { AlbumSongMap.albumId eq id }
        Albums.deleteWhere { Albums.id eq id }
    }
}
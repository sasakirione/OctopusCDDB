package com.planet.lily.cddb.model

import com.planet.lily.cddb.entity.*
import com.planet.lily.cddb.plugins.*
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

    fun getAlbumList(text: String) = transaction {
        Albums.select { Albums.title like text }.map { it[Albums.id].value to (it[Albums.title] + " " + it[Albums.albumVersion].orEmpty()) }
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

    fun getSongsForAlbum(id: Int) = transaction {
        val songs = AlbumSongMap.innerJoin(Songs).select { AlbumSongMap.albumId eq id }.toList()
        songs.map {
            val artistPair = getArtist(it[Songs.id].value)
            AlbumSongJson2(
                discNumber = it[AlbumSongMap.discNumber],
                trackNumber = it[AlbumSongMap.trackNumber],
                title = it[Songs.title],
                artist = artistPair.first,
                artistId = artistPair.second,
                word = getWords(it[Songs.id].value),
                composer = getComposer(it[Songs.id].value),
                arranger = getArranger(it[Songs.id].value),
                originalSongId = it[Songs.originalId].value)
        }
    }

    private fun getArtist(id: Int): Pair<String, Int> {
        val res = SongArtistMap.innerJoin(Artists).select { SongArtistMap.songId eq id }
            .map { it[Artists.name] to it[Artists.id] }
        val names = res.joinToString(",") { it.first }
        val ids = 1
        return names to ids
    }

    private fun getWords(id: Int): List<CreatorJson2> =
        Lyricists.innerJoin(MusicianNameMap).innerJoin(LyricistTypes).select { Lyricists.songId eq id }.map {
            CreatorJson2(it[LyricistTypes.name], it[MusicianNameMap.name], it[MusicianNameMap.id].value)
        }

    private fun getComposer(id: Int): List<CreatorJson2> =
        Composers.innerJoin(MusicianNameMap).select { Composers.songId eq id }.map {
            CreatorJson2("作曲", it[MusicianNameMap.name], it[MusicianNameMap.id].value)
        }

    private fun getArranger(id: Int): List<CreatorJson2> =
        Arrangers.innerJoin(MusicianNameMap).innerJoin(ArrangeTypes).select { Arrangers.songId eq id }.map {
            CreatorJson2(it[ArrangeTypes.name], it[MusicianNameMap.name], it[MusicianNameMap.id].value)
        }
}
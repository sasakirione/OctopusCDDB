package com.planet.lily.cddb.model

import com.planet.lily.cddb.entity.Artists
import com.planet.lily.cddb.entity.OriginalSongs
import com.planet.lily.cddb.plugins.OriginalSongJson
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class OriginalSong {
    fun getOriginalSongList(): List<OriginalSongJson> = transaction {
        OriginalSongs.innerJoin(Artists).selectAll().map { OriginalSongJson(it[OriginalSongs.title], it[Artists.name]) }
    }

    fun getOriginalSong(id: Int): OriginalSongJson = transaction {
        OriginalSongs.innerJoin(Artists).select { OriginalSongs.id eq id }.map { OriginalSongJson(it[OriginalSongs.title], it[Artists.name]) }.first()
    }

    fun addOriginalSong(song: OriginalSongJson): Int = transaction {
        val songId = OriginalSongs.insert {
            it[title] = song.title
            it[originalArtist] = Artists.select { Artists.name eq song.artist }.first()[Artists.id]
        } get OriginalSongs.id
        songId.value
    }

    fun updateOriginalSong(id: Int, song: OriginalSongJson) = transaction {
        OriginalSongs.innerJoin(Artists).update({OriginalSongs.id eq id}){ it[OriginalSongs.title] = song.title; it[Artists.name] = song.artist }
    }

    fun deleteOriginalSong(id: Int) = transaction {
        OriginalSongs.deleteWhere { OriginalSongs.id eq id }
    }
}